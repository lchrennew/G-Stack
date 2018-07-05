package chun.li.GStack.SuiteParser;

import java.util.List;

public class Spec {
    String title;
    Integer lineNumber;
    final List<String> tags;
    final List<Scenario> scenarios;

    public Spec(String title, int lineNumber, List<String> tags, List<Scenario> scenarios) {
        this.title = title;
        this.lineNumber = lineNumber;
        this.tags = tags;
        this.scenarios = scenarios;
    }
}
