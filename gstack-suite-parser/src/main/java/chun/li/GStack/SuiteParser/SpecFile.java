package chun.li.GStack.SuiteParser;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.File;

public class SpecFile {
    final String file;
    Spec spec;

    public SpecFile(String file) {
        this.file = "." + file.substring(file.indexOf(File.separatorChar));
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
    }

    @JsonProperty("file")
    public String getFile() {
        return file;
    }

    @JsonProperty("spec")
    public Spec getSpec() {
        return spec;
    }
}
