package chun.li.GStack.SuiteParser;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static org.apache.calcite.linq4j.Linq4j.asEnumerable;

public class SpecIndexer {

    private static final int SPEC = 1;
    private static final int SCENARIO = 2;
    private static final MutableDataSet options = new MutableDataSet();

    private final static Pattern tagsPattern = Pattern.compile("\\ATags:(.+)\\z", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    private static List<String> getTags(String tagsStr) {
        try {
            Matcher argMatcher = tagsPattern.matcher(tagsStr);
            if (argMatcher.find()) {
                return
                        asEnumerable(argMatcher.group(1).trim().split(","))
                                .select(String::trim)
                                .skipWhile(String::isEmpty)
                                .toList();
            }
        } catch (PatternSyntaxException ex) {
            // Syntax error in the regular expression
        }
        return null;
    }

    public static List<SpecFile> buildIndex(String... files) throws FileNotFoundException {
        List<SpecFile> idx = new ArrayList<>();
        files = asEnumerable(files)
                .selectMany(file -> asEnumerable(retrieveSpecs(file, ".spec")))
                .toList()
                .toArray(new String[0]);
        for (String file :
                files) {

            SpecFile spedFile = new SpecFile(file);
            spedFile.addSpecs(loadSpecs(file));
            idx.add(spedFile);
        }
        return idx;
    }

    private static List<Spec> loadSpecs(String file) {
        String fileContent = loadTextFile(file);
        return parseMarkdown(fileContent);
    }

    public static List<Spec> parseMarkdown(String fileContent) {
        List<Spec> specs = new ArrayList<>();
        Parser parser = Parser.builder(options).build();
        Document document = parser.parse(fileContent);
        Heading heading = (Heading) document.getFirstChildAny(Heading.class);
        Spec spec = null;
        List<Scenario> scenarios = null;


        while (heading != null) {
            int level = heading.getLevel();
            String title = heading.getText().toString();
            Integer lineNumber = heading.getLineNumber();
            List<String> tags = getTags(heading);
            if (level == SPEC) {
                scenarios = new ArrayList<>();
                specs.add(spec = new Spec(title, lineNumber, tags, scenarios));
            } else if (scenarios != null) {
                scenarios.add(new Scenario(title, lineNumber, tags));
            }
            heading = (Heading) heading.getNextAny(Heading.class);
        }


        return specs;
    }

    private static List<String> getTags(Heading heading) {
        Paragraph p = (Paragraph) heading.getNextAny(Paragraph.class);
        List<String> tags = new ArrayList<>();
        if (p != null) {
            tags = getTags(p.getFirstChildAny(Text.class).getChars().toString());
        }
        return tags;
    }

    private static String loadTextFile(String file) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public static String[] retrieveSpecs(String path, String filePattern) {
        System.out.print(new File("").getAbsolutePath());
        Stack<File> dirs = new Stack<>();
        List<File> files = new ArrayList<>();

        File f0 = new File(path);
        if (f0.isDirectory()) {
            dirs.push(f0);
            while (!dirs.empty()) {
                File d0 = dirs.pop();
                File[] d1 = d0.listFiles(File::isDirectory);
                for (File d : requireNonNull(d1)) {
                    dirs.push(d);
                }
                File[] f1 = d0.listFiles(File::isFile);
                files.addAll(asList(requireNonNull(f1)));
            }
        } else
            files.add(f0);

        return asEnumerable(files)
                .where(file -> file.getName().endsWith(filePattern))
                .select(File::getPath).toList()
                .toArray(new String[0]);
    }
}
