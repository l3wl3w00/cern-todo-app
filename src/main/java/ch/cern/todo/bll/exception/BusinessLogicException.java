package ch.cern.todo.bll.exception;

import ch.cern.todo.api.response.Response;
import ch.cern.todo.bll.dto.NoContentDTO;

import java.util.function.Supplier;

public abstract class BusinessLogicException extends RuntimeException {
    public BusinessLogicException(String message) {
        super(message);
    }

    public abstract <DTO> Response<DTO> createResponse();
}
