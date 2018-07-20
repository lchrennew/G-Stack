package chun.li.GStack.Console.api.repositories;

import chun.li.GStack.Console.api.domain.Suite;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface SuiteRepository extends Neo4jRepository<Suite, Long> {
    Suite findByTitle(@Param("title") String suite);

    void deleteByTitle(@Param("title") String title);

    boolean existsByTitle(@Param("title") String title);
}
