package ch.cern.todo.bll.interfaces;

public interface CRUDService<T> {
    T getAll();
    T getById(Long id);
    T add(T t);
    T update(T t);
    T deleteById(Long id);
}
