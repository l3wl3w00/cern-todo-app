package ch.cern.todo.bll.exception;

public class InvalidDTOException extends RuntimeException {
    public InvalidDTOException(String message) {
        super(message);
    }
}
