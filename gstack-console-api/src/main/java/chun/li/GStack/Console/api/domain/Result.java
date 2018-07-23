package chun.li.GStack.Console.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import static org.neo4j.ogm.annotation.Relationship.INCOMING;

@NodeEntity
public class Result {


    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty
    private String shell;

    @JsonProperty
    private String report;

    @JsonProperty
    private Boolean succeeded;

    @JsonProperty
    private String output;

    @Relationship(type = "RESULT_IN", direction = INCOMING)
    private Suite suite;

    public Result() {
    }

    public Result(Suite suite, String shell, String report, Boolean succeeded, String output) {
        this.suite = suite;
        this.shell = shell;
        this.report = report;
        this.succeeded = succeeded;
        this.output = output;
    }
}
