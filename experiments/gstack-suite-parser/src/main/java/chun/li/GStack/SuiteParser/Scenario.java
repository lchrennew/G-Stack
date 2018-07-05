package chun.li.GStack.SuiteParser;

import org.codehaus.jackson.annotate.JsonProperty;

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
}
