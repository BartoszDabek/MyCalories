package pl.mycalories.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycalories.service.AbstractService;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractServiceImpl<T, ID extends Serializable, U extends JpaRepository<T, ID>>
        implements AbstractService<T, ID> {

    protected U repository;

    protected AbstractServiceImpl(U repository) {
        this.repository = repository;
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public T save(T t) {
        return repository.save(t);
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public T findById(ID id) {
        Optional<T> byId = repository.findById(id);
        return byId.orElseThrow(RuntimeException::new);
    }
}
