package chun.li.GStack.SuiteParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.util.List;

import static chun.li.GStack.SuiteParser.SpecIndexer.buildIndex;

public class Demo {

    public static void main(String[] args) throws FileNotFoundException {

        List<SpecFile> idx = buildIndex(null);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println(gson.toJson(idx));
    }

}
