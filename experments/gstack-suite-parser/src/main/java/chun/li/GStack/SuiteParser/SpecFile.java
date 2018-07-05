package chun.li.GStack.SuiteParser;

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
}
