package chun.li.GStack.SuiteParser;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class SpecFile {
    final String file;
    final List<Spec> specs = new ArrayList<>();

    public SpecFile(String file) {
        this.file = file;
    }

    public void addSpecs(List<Spec> specs) {
        this.specs.addAll(specs);
    }

    @JsonProperty("file")
    public String getFile() {
        return file;
    }

    @JsonProperty("specs")
    public List<Spec> getSpecs() {
        return specs;
    }
}
