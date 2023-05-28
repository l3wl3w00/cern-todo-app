package ch.cern.todo.bll.exception;

import ch.cern.todo.api.response.Response;
import ch.cern.todo.bll.dto.NoContentDTO;

public class EntityAlreadyExistsException extends BusinessLogicException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public <DTO> Response<DTO> createResponse() {
        return Response.conflict(getMessage());
    }
}
