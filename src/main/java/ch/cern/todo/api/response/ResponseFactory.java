package ch.cern.todo.api.response;

import ch.cern.todo.bll.exception.BusinessLogicException;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class ResponseFactory {

    public <DTO> Response okOrHandleError(Supplier<DTO> throwingFunction) {
        return tryCatch(() -> ContentResponse.ok(throwingFunction.get()));
    }

    public Response deletedOrHandleError(Runnable throwingFunction) {
        return tryCatch(() -> {
            throwingFunction.run();
            return ContentResponse.deleted();
        });
    }

    public <DTO> Response createdOrHandleError(Supplier<DTO> throwingFunction) {
        return tryCatch(() -> ContentResponse.created(throwingFunction.get()));
    }

    public <DTO> Response ok(Supplier<DTO> contentSupplier) {
        return ContentResponse.ok(contentSupplier.get());
    }

    private Response tryCatch(Supplier<Response> throwingFunction) {
        try {
            return throwingFunction.get();
        } catch (BusinessLogicException e) {
            return e.createResponse();
        }
    }
}
