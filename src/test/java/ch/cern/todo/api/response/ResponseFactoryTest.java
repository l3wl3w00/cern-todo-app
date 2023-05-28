package ch.cern.todo.api.response;

import ch.cern.todo.bll.dto.NoContentDTO;
import ch.cern.todo.bll.exception.BusinessLogicException;
import ch.cern.todo.bll.exception.EntityAlreadyExistsException;
import ch.cern.todo.bll.exception.EntityNotFoundException;
import ch.cern.todo.bll.exception.InvalidDTOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResponseFactoryTest {
    private ResponseFactory responseFactory;
    private String exceptionMessage;

    @BeforeEach
    void setUp() {
        exceptionMessage = "Exception Message";
        responseFactory = new ResponseFactory();
    }
    @Test
    void okOrHandleError_WithValidSupplier_ShouldReturnOkResponse() {
        exceptionMessage = ""; // when everything is ok, there should be no exception message
        okActAndAssert(HttpStatus.OK, NoContentDTO::new);
    }


    @Test
    void okOrHandleError_WithThrowingEntityAlreadyExistsException_ShouldHandleConflict() {
        okActAndAssert(HttpStatus.CONFLICT, () -> {
            throw new EntityAlreadyExistsException(exceptionMessage);
        });
    }

    @Test
    void okOrHandleError_WithThrowingEntityNotFoundException_ShouldHandleNotFound() {
        okActAndAssert(HttpStatus.NOT_FOUND, () -> {
            throw new EntityNotFoundException(exceptionMessage);
        });
    }

    @Test
    void okOrHandleError_WithThrowingInvalidDTOException_ShouldHandleBadRequest() {
        okActAndAssert(HttpStatus.BAD_REQUEST, () -> {
            throw new InvalidDTOException(exceptionMessage);
        });
    }

    @Test
    void deletedOrHandleError_WithValidRunnable_ShouldReturnDeletedResponse() {
        exceptionMessage = ""; // when everything is ok, there should be no exception message
        deleteActAndAssert(HttpStatus.NO_CONTENT,() -> {});
    }

    @Test
    void deletedOrHandleError_WithThrowingRunnable_ShouldHandleConflict() {
        deleteActAndAssert(HttpStatus.CONFLICT,() -> {
            throw new EntityAlreadyExistsException(exceptionMessage);
        });
    }

    @Test
    void deletedOrHandleError_WithThrowingRunnable_ShouldHandleNotFound() {
        deleteActAndAssert(HttpStatus.NOT_FOUND,() -> {
            throw new EntityNotFoundException(exceptionMessage);
        });
    }

    @Test
    void deletedOrHandleError_WithThrowingRunnable_ShouldHandleBadRequest() {
        deleteActAndAssert(HttpStatus.BAD_REQUEST,() -> {
            throw new InvalidDTOException(exceptionMessage);
        });
    }

    @Test
    void createdOrHandleError_WithValidSupplier_ShouldReturnCreatedResponse() {
        exceptionMessage = ""; // when everything is ok, there should be no exception message
        createdActAndAssert(HttpStatus.CREATED,NoContentDTO::new);
    }

    @Test
    void createdOrHandleError_WithValidSupplier_ShouldHandleConflict() {
        createdActAndAssert(HttpStatus.CONFLICT,() -> {
            throw new EntityAlreadyExistsException(exceptionMessage);
        });
    }

    @Test
    void createdOrHandleError_WithValidSupplier_ShouldHandleNotFound() {
        createdActAndAssert(HttpStatus.NOT_FOUND,() -> {
            throw new EntityNotFoundException(exceptionMessage);
        });
    }

    @Test
    void createdOrHandleError_WithValidSupplier_ShouldHandleBadRequest() {
        createdActAndAssert(HttpStatus.BAD_REQUEST,() -> {
            throw new InvalidDTOException(exceptionMessage);
        });
    }

    void deleteActAndAssert(HttpStatus status, Runnable throwingRunnable) {
        actAndAssert(status, () -> responseFactory.deletedOrHandleError(throwingRunnable));
    }

    void okActAndAssert(HttpStatus status, Supplier<NoContentDTO> throwingFunction) {
        actAndAssert(status, () -> responseFactory.okOrHandleError(throwingFunction));
    }

    void createdActAndAssert(HttpStatus status, Supplier<NoContentDTO> throwingFunction) {
        actAndAssert(status, () -> responseFactory.createdOrHandleError(throwingFunction));
    }

    void actAndAssert(HttpStatus status, Supplier<Response<NoContentDTO>> responseSupplier) {
        assertResponseAlright(status, exceptionMessage, /* Act: */ responseSupplier.get());
    }

    private <DTO> void assertResponseAlright(HttpStatus status, String exceptionMessage, Response<DTO> response) {
        assertEquals(status.getReasonPhrase(), response.getStatusName());
        assertEquals(status.value(), response.getStatusCode());
        assertEquals(exceptionMessage,response.getAdditionalInfo());
    }


}