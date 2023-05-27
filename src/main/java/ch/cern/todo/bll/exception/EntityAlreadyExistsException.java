package ch.cern.todo.bll.exception;

import ch.cern.todo.api.response.Response;

public class EntityAlreadyExistsException extends BusinessLogicException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public Response createResponse() {
        return Response.conflict(getMessage());
    }
}
