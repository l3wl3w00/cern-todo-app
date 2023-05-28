package ch.cern.todo.bll.exception;

import ch.cern.todo.api.response.Response;
import ch.cern.todo.bll.dto.NoContentDTO;

public class InvalidDTOException extends BusinessLogicException {
    public InvalidDTOException(String message) {
        super(message);
    }

    @Override
    public <DTO> Response<DTO> createResponse() {
        return Response.badRequest(getMessage());
    }
}
