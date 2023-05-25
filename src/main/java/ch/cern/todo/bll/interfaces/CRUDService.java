package ch.cern.todo.bll.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface CRUDService<T> {
    T getAll();
    T getById(Long id);
    T add(T t);
    T update(T t);
    T deleteById(Long id);
}
