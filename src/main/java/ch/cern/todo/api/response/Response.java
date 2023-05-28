package ch.cern.todo.api.response;

import ch.cern.todo.bll.constants.EnglishStrings;
import ch.cern.todo.bll.dto.NoContentDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response<CONTENT_TYPE> {
    String statusName;
    Integer statusCode;
    String description = "";
    String additionalInfo = "";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    CONTENT_TYPE content;


    protected void setStatus(HttpStatus status) {
        setStatusCode(status.value());
        setStatusName(status.getReasonPhrase());
    }

    public static <CONTENT_TYPE> Response<CONTENT_TYPE> conflict(String message) {
        Response<CONTENT_TYPE> response =  new Response<>();
        response.setStatus(HttpStatus.CONFLICT);
        response.setDescription(EnglishStrings.CONSTRAINT_VIOLATION.getValue());
        response.setAdditionalInfo(message);
        return response;
    }
    public static <CONTENT_TYPE> Response<CONTENT_TYPE> badRequest(String message) {
        Response<CONTENT_TYPE> response =  new Response<>();
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setDescription(EnglishStrings.INVALID_REQUEST.getValue());
        response.setAdditionalInfo(message);
        return response;
    }
    public static <CONTENT_TYPE> Response<CONTENT_TYPE> notFound() {
        return notFound("");
    }
    public static <CONTENT_TYPE> Response<CONTENT_TYPE> notFound(String message) {
        Response<CONTENT_TYPE> response =  new Response<>();
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setDescription(EnglishStrings.RESOURCE_DOESNT_EXIST.getValue());
        response.setAdditionalInfo(message);
        return response;
    }
    public static Response<NoContentDTO> deleted() {
        Response<NoContentDTO> response =  new Response<>();
        response.setStatus(HttpStatus.NO_CONTENT);
        response.setDescription(EnglishStrings.RESOURCE_WAS_DELETED.getValue());
        return response;
    }


    public static <CONTENT_TYPE> Response<CONTENT_TYPE> ok(CONTENT_TYPE dto) {
        Response<CONTENT_TYPE> contentResponse =  new Response<>();

        contentResponse.setContent(dto);
        contentResponse.setStatus(HttpStatus.OK);

        return contentResponse;
    }

    public static <CONTENT_TYPE> Response<CONTENT_TYPE> created(CONTENT_TYPE dto) {
        Response<CONTENT_TYPE> contentResponse =  new Response<>();

        contentResponse.setContent(dto);
        contentResponse.setStatus(HttpStatus.CREATED);

        return contentResponse;
    }
}
