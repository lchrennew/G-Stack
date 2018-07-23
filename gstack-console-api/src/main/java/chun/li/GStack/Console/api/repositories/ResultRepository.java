package chun.li.GStack.Console.api.repositories;

import chun.li.GStack.Console.api.domain.Result;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface ResultRepository extends Neo4jRepository<Result, Long> {
    @Query("MATCH (s:Suite)-[:RESULT_IN]->(r:Result) WHERE s.title = $suite RETURN r")
    Iterable<Result> findAllBySuite(@Param("suite") String suite);
}
