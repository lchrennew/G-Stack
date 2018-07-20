package chun.li.GStack.Console.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class Suite {
    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String description;

    @JsonIgnore
    @Relationship(type = "RESULT_IN")
    private List<Result> results;
}
