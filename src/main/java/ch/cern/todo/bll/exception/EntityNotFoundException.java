package ch.cern.todo.bll.exception;

public class EntityNotFoundException extends RuntimeException {
    public <T> EntityNotFoundException(Class<T> type, Long id) {
        super(String.format("No %s entity exists with id %d", type.getSimpleName(), id));
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
