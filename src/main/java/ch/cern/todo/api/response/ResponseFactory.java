package ch.cern.todo.api.response;

import ch.cern.todo.api.response.ContentResponse;
import ch.cern.todo.api.response.Response;
import ch.cern.todo.bll.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class ResponseFactory {

    public <DTO> Response okOrNotFound(Supplier<DTO> throwingFunction){
        try {
            return ContentResponse.ok(throwingFunction.get());
        } catch (EntityNotFoundException e) {
            return Response.notFound(e.getMessage());
        }
    }

    public Response deletedOrNotFound(Runnable throwingFunction) {
        try {
            throwingFunction.run();
            return ContentResponse.deleted();
        } catch (EntityNotFoundException e) {
            return Response.notFound(e.getMessage());
        }
    }

    public <DTO> Response createdOrNotFound(Supplier<DTO> throwingFunction) {
        try {
            return ContentResponse.created(throwingFunction.get());
        } catch (EntityNotFoundException e) {
            return Response.notFound(e.getMessage());
        }
    }

    public <DTO> Response ok(Supplier<DTO> contentSupplier) {
        return ContentResponse.ok(contentSupplier.get());
    }
}
