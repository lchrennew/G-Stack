package chun.li.GStack.Console.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Suite {
    @Id
    @GeneratedValue
    private Long id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String description;

    public Long getId() {
        return id;
    }
}
