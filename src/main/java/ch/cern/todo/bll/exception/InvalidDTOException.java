package ch.cern.todo.bll.exception;

import ch.cern.todo.api.response.Response;

public class InvalidDTOException extends BusinessLogicException {
    public InvalidDTOException(String message) {
        super(message);
    }

    @Override
    public Response createResponse() {
        return Response.badRequest(getMessage());
    }
}
