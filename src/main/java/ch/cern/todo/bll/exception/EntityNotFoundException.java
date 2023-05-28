package ch.cern.todo.bll.exception;

import ch.cern.todo.api.response.Response;
import ch.cern.todo.bll.constants.EnglishStrings;
import ch.cern.todo.bll.dto.NoContentDTO;

public class EntityNotFoundException extends BusinessLogicException {
    public <T> EntityNotFoundException(Class<T> type, Long id) {
        super(EnglishStrings.NO_ENTITY.formatted(type.getSimpleName(), id));
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    @Override
    public <DTO> Response<DTO> createResponse() {
        return Response.notFound(getMessage());
    }
}
