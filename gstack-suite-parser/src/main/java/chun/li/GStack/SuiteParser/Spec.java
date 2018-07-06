package chun.li.GStack.SuiteParser;

import org.codehaus.jackson.annotate.JsonProperty;

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

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("line")
    public Integer getLineNumber() {
        return lineNumber;
    }

    @JsonProperty("tags")
    public List<String> getTags() {
        return tags;
    }

    @JsonProperty("scenarios")
    public List<Scenario> getScenarios() {
        return scenarios;
    }
}
