package ch.cern.todo.bll.exception;

import ch.cern.todo.api.response.Response;
import ch.cern.todo.bll.constants.EnglishStrings;

public class EntityNotFoundException extends BusinessLogicException {
    public <T> EntityNotFoundException(Class<T> type, Long id) {
        super(String.format(EnglishStrings.NO_ENTITY.getValue(), type.getSimpleName(), id));
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    @Override
    public Response createResponse() {
        return Response.notFound(getMessage());
    }
}
