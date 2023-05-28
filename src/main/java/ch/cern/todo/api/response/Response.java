package ch.cern.todo.api.response;

import ch.cern.todo.bll.dto.NoContentDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response<DTO> {
    private String statusName;
    private Integer statusCode;
    private String description = "";
    private String additionalInfo = "";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    DTO content;


    protected void setStatus(HttpStatus status) {
        setStatusCode(status.value());
        setStatusName(status.getReasonPhrase());
    }

    public static <DTO> Response<DTO> conflict(String message) {
        Response<DTO> response =  new Response<DTO>();
        response.setStatus(HttpStatus.CONFLICT);
        response.setDescription("The requested operation cannot be performed due to a constraint violation");
        response.setAdditionalInfo(message);
        return response;
    }
    public static <DTO> Response<DTO> badRequest(String message) {
        Response<DTO> response =  new Response<DTO>();
        response.setStatus(HttpStatus.BAD_REQUEST);
        response.setDescription("The request is invalid");
        response.setAdditionalInfo(message);
        return response;
    }
    public static <DTO> Response<DTO> notFound() {
        return notFound("");
    }
    public static <DTO> Response<DTO> notFound(String message) {
        Response<DTO> response =  new Response<DTO>();
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setDescription("The resource you are trying to access does not exist");
        response.setAdditionalInfo(message);
        return response;
    }
    public static Response<NoContentDTO> deleted() {
        Response<NoContentDTO> response =  new Response<NoContentDTO>();
        response.setStatus(HttpStatus.NO_CONTENT);
        response.setDescription("The resource was deleted");
        return response;
    }


    public static <DTO> Response<DTO> ok(DTO dto) {
        Response<DTO> contentResponse =  new Response<>();

        contentResponse.setContent(dto);
        contentResponse.setStatus(HttpStatus.OK);

        return contentResponse;
    }

    public static <DTO> Response<DTO> created(DTO dto) {
        Response<DTO> contentResponse =  new Response<>();

        contentResponse.setContent(dto);
        contentResponse.setStatus(HttpStatus.CREATED);

        return contentResponse;
    }
}
