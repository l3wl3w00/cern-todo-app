package ch.cern.todo.api.response;

import lombok.*;
import org.springframework.http.HttpStatus;


public class ContentResponse<DTO> extends Response{
    @Getter
    @Setter
    DTO dto;


    public static <DTO> ContentResponse<DTO> ok(DTO dto){
        ContentResponse<DTO> contentResponse =  new ContentResponse<>();

        contentResponse.setDto(dto);
        contentResponse.setStatus(HttpStatus.OK);

        return contentResponse;
    }

    public static <DTO> ContentResponse<DTO> created(DTO dto){
        ContentResponse<DTO> contentResponse =  new ContentResponse<>();

        contentResponse.setDto(dto);
        contentResponse.setStatus(HttpStatus.CREATED);

        return contentResponse;
    }
}
