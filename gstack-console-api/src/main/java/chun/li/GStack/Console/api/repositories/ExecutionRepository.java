package chun.li.GStack.Console.api.repositories;

import chun.li.GStack.Console.api.domain.Execution;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ExecutionRepository extends Neo4jRepository<Execution, Long> {

}
