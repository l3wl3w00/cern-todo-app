package ch.cern.todo.bll.interfaces;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface CRUDService<T> {
    Collection<T> getAll();
    T getById(Long id);
    T add(T t);
    T update(T t);
    T deleteById(Long id);
}
