package ch.cern.todo.api.response;

import ch.cern.todo.bll.dto.NoContentDTO;
import ch.cern.todo.bll.exception.BusinessLogicException;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class ResponseFactory {

    public <DTO> Response<DTO> okOrHandleError(Supplier<DTO> throwingFunction) {
        return tryCatch(() -> Response.ok(throwingFunction.get()));
    }

    public Response<NoContentDTO> deletedOrHandleError(Runnable throwingFunction) {
        return tryCatch(() -> {
            throwingFunction.run();
            return Response.deleted();
        });
    }

    public <DTO> Response<DTO> createdOrHandleError(Supplier<DTO> throwingFunction) {
        return tryCatch(() -> Response.created(throwingFunction.get()));
    }

    public <DTO> Response<DTO> ok(Supplier<DTO> responseContentSupplier) {
        return Response.ok(responseContentSupplier.get());
    }

    private <DTO> Response<DTO> tryCatch(Supplier<Response<DTO>> throwingFunction) {
        try {
            return throwingFunction.get();
        } catch (BusinessLogicException e) {
            return e.createResponse();
        }
    }
}
