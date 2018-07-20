package chun.li.GStack.Console.api.services;

import chun.li.GStack.Console.api.domain.Suite;
import chun.li.GStack.Console.api.repositories.SuiteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.apache.calcite.linq4j.Linq4j.asEnumerable;

@Service
public class SuiteService {
    private final SuiteRepository repository;

    public SuiteService(SuiteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Collection<Suite> getAll() {
        return asEnumerable(repository.findAll()).toList();
    }

    @Transactional
    public Suite save(Suite suite) {
        return repository.save(suite);
    }

    @Transactional(readOnly = true)
    public Suite findByTitle(String title) {
        return repository.findByTitle(title);
    }

    @Transactional
    public boolean delete(Long id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    public boolean deleteByTitle(String title) {
        repository.deleteByTitle(title);
        return !repository.existsByTitle(title);
    }
}
