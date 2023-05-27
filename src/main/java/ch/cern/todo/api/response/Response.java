package ch.cern.todo.api.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response {
    String statusName;
    Integer statusCode;
    String description = "";
    protected void setStatus(HttpStatus status){
        setStatusCode(status.value());
        setStatusName(status.getReasonPhrase());
    }
    public static Response noContent(){
        Response response =  new Response();

        response.setStatus(HttpStatus.NO_CONTENT);

        return response;
    }
}
