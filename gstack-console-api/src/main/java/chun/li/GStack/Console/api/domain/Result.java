package chun.li.GStack.Console.api.domain;

import org.codehaus.jackson.annotate.JsonProperty;
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
    private String report;

    @JsonProperty
    private Boolean succeeded;

    @Relationship(type = "RESULT_IN", direction = INCOMING)
    private Suite suite;

    public Result() {
        report = null;
        succeeded = false;
    }

    public Result(String report, Boolean succeeded, Suite suite) {
        this.report = report;
        this.succeeded = succeeded;
        this.suite = suite;
    }
}
