package chun.li.GStack.Console.api.domain;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "RESULT_IN")
public class Execution {
    @Id
    @GeneratedValue
    Long id;

    private String shell;

    @StartNode
    private Suite suite;
    @EndNode
    private Result result;

    public Execution() {
        shell = null;
        suite = null;
        result = null;
    }

    public Execution(String shell, Suite suite, Result result) {
        this.shell = shell;
        this.suite = suite;
        this.result = result;
    }
}
