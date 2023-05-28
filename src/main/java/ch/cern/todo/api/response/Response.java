package ch.cern.todo.api.response;

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
        response.setDescription("The requested operation cannot be performed due to a constraint violation");
        response.setAdditionalInfo(message);
        return response;
    }
    public static <CONTENT_TYPE> Response<CONTENT_TYPE> badRequest(String message) {
        Response<CONTENT_TYPE> response =  new Response<>();
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setDescription("The request is invalid");
        response.setAdditionalInfo(message);
        return response;
    }
    public static <CONTENT_TYPE> Response<CONTENT_TYPE> notFound() {
        return notFound("");
    }
    public static <CONTENT_TYPE> Response<CONTENT_TYPE> notFound(String message) {
        Response<CONTENT_TYPE> response =  new Response<>();
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setDescription("The resource you are trying to access does not exist");
        response.setAdditionalInfo(message);
        return response;
    }
    public static Response<NoContentDTO> deleted() {
        Response<NoContentDTO> response =  new Response<>();
        response.setStatus(HttpStatus.NO_CONTENT);
        response.setDescription("The resource was deleted");
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
