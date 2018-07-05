package chun.li.GStack.SuiteParser;

import java.util.List;

public class Scenario {
    String title;
    Integer lineNumber;
    Integer steps;
    List<String> tags;

    public Scenario(String title, Integer lineNumber, List<String> tags) {
        this.title = title;
        this.lineNumber = lineNumber;
        this.tags = tags;
    }
}
