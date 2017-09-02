package pl.mycalories.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycalories.service.AbstractService;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractServiceImpl<T, ID extends Serializable> implements AbstractService<T, ID> {

    private JpaRepository<T, ID> repository;

    protected AbstractServiceImpl(JpaRepository<T, ID> repository) {
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
        return repository.findOne(id);
    }
}
