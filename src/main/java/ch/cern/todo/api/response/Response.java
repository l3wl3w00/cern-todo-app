package ch.cern.todo.api.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Response<DTO> {
    DTO dto;
    HttpStatus status;
    String description;

    public static <DTO> Response<DTO> ok(DTO dto){
        Response<DTO> response =  new Response<>();

        response.setDto(dto);
        response.setStatus(HttpStatus.OK);
        response.setDescription("");

        return response;
    }
}
