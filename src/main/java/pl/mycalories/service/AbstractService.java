package pl.mycalories.service;

import java.io.Serializable;
import java.util.List;

public interface AbstractService<T, ID extends Serializable> {

    List<T> getAll();

    T save(T t);

    void delete(T t);

    T findById(ID id);
}
