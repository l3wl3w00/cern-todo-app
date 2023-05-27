package ch.cern.todo.bll.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String name) {
        super(String.format("No category entity exists with name %s", name));
    }
}
