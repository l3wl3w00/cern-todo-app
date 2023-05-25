package ch.cern.todo.bll.exception;

import java.util.ArrayList;
import java.util.Objects;

public class NoSuchEntityException extends RuntimeException {
    public NoSuchEntityException(Class type, Long id) {
        super(String.format("No %s entity exists with id %d", type.getName(), id));
    }
}
