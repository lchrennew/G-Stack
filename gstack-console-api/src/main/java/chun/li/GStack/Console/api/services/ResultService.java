package chun.li.GStack.Console.api.services;

import chun.li.GStack.Console.api.domain.Result;
import chun.li.GStack.Console.api.repositories.ResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResultService {
    private final ResultRepository repository;

    public ResultService(ResultRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Result save(Result result) {
        return repository.save(result);
    }

    @Transactional(readOnly = true)
    public Iterable<Result> findBySuiteTitle(String title) {
        return repository.findAllBySuite(title);
    }
}
