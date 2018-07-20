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
        return new ArrayList<>();
    }

    public static List<SpecFile> buildIndex(String root, String... files) throws FileNotFoundException {
        List<SpecFile> idx = new ArrayList<>();
        if (files == null || files.length == 0)
            files = new String[]{root};
        files = asEnumerable(files)
                .selectMany(file -> asEnumerable(retrieveSpecs(root, file, ".spec")))
                .toList()
                .toArray(new String[0]);
        for (String file :
                files) {

            SpecFile spedFile = new SpecFile(file);
            Spec spec = loadSpec(root, file);
            if (spec != null) {
                spedFile.setSpec(loadSpec(root, file));
                idx.add(spedFile);
            }
        }
        return idx;
    }

    private static Spec loadSpec(String root, String file) {
        String fileContent = loadTextFile(root, file);
        return parseMarkdown(fileContent);
    }

    public static Spec parseMarkdown(String fileContent) {
        List<Spec> specs = new ArrayList<>();
        Parser parser = Parser.builder(options).build();
        Document document = parser.parse(fileContent);
        Heading heading = (Heading) document.getFirstChildAny(Heading.class);
        Spec spec = null;
        List<Scenario> scenarios = null;


        while (heading != null) {
            int level = heading.getLevel();
            String title = heading.getText().toString();
            Integer lineNumber = heading.getLineNumber() + 1;
            List<String> tags = getTags(heading);
            if (level == SPEC) {
                scenarios = new ArrayList<>();
                specs.add(spec = new Spec(title, lineNumber, tags, scenarios));
            } else if (scenarios != null) {
                scenarios.add(new Scenario(title, lineNumber, tags));
            }
            heading = (Heading) heading.getNextAny(Heading.class);
        }

        if (specs.size() > 1) {
            throw new UnsupportedOperationException(
                    String.format("There should be only one spec in each Spec file!\n%s", fileContent));
        } else if (specs.size() == 0)
            return null;
        return specs.get(0);
    }

    private static List<String> getTags(Heading heading) {
        Paragraph p = (Paragraph) heading.getNextAny(Paragraph.class);
        List<String> tags = new ArrayList<>();
        if (p != null) {
            tags = getTags(p.getFirstChildAny(Text.class).getChars().toString());
        }
        return tags;
    }

    private static String loadTextFile(String root, String file) {
        StringBuilder contentBuilder = new StringBuilder();
        File textFile = new File(root, file);
        try (BufferedReader br =
                     new BufferedReader(new FileReader(textFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    private static String[] retrieveSpecs(String root, String path, String filePattern) {
        if (root == null) root = ".";
        File fr = new File(root),
                f0 = path != null ? new File(fr, path) : fr;

        Stack<File> dirs = new Stack<>();
        List<File> files = new ArrayList<>();

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
        final int rootLength = fr.getPath().length() + 1;
        return asEnumerable(files)
                .where(file -> file.getName().endsWith(filePattern))
                .select(file -> {
                    return file.getPath().substring(rootLength);
                })
                .toList()
                .toArray(new String[0]);
    }
}
