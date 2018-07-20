package chun.li.GStack.Console.api.repositories;

import chun.li.GStack.Console.api.domain.Result;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ResultRepository extends Neo4jRepository<Result, Long> {
    @Query("MATCH (s:Suite)-[s:GENERATED]->(r:Result) WHERE s.name = $suite RETURN r,s")
    Collection<Result> findAllBySuite(@Param("suite") String suite);
}
