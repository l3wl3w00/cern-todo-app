package ch.cern.todo.api.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response {
    String statusName;
    Integer statusCode;
    String description = "";
    String additionalInfo = "";

    protected void setStatus(HttpStatus status){
        setStatusCode(status.value());
        setStatusName(status.getReasonPhrase());
    }

    public static Response notFound() {
        return notFound("");
    }
    public static Response notFound(String message) {
        Response response =  new Response();
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setDescription("The resource you are trying to access does not exist");
        response.setAdditionalInfo(message);
        return response;
    }
    public static Response deleted() {
        Response response =  new Response();
        response.setStatus(HttpStatus.NO_CONTENT);
        response.setDescription("The resource was deleted");
        return response;
    }
}
