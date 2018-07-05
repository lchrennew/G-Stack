package chun.li.GStack.SuiteParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static chun.li.GStack.SuiteParser.SpecIndexer.buildIndex;
import static java.util.Arrays.asList;
import static java.util.Objects.*;
import static org.apache.calcite.linq4j.Linq4j.asEnumerable;

public class Markdown {

    public static void main(String[] args) throws FileNotFoundException {
        String[] filenames = retrieveSpecs(".", ".spec");
        List<SpecFile> idx = buildIndex(filenames);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println(gson.toJson(idx));
    }

    private static String[] retrieveSpecs(String path, String filePattern) {
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
