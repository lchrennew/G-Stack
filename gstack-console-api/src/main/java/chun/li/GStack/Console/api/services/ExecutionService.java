package chun.li.GStack.Console.api.services;

import chun.li.GStack.Console.api.domain.Execution;
import chun.li.GStack.Console.api.repositories.ExecutionRepository;
import org.springframework.stereotype.Service;

@Service
public class ExecutionService {
    private final ExecutionRepository repository;

    public ExecutionService(ExecutionRepository repository) {
        this.repository = repository;
    }

    public Execution save(Execution execution) {
        return repository.save(execution);
    }
}
