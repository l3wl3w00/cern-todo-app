package ch.cern.todo.bll.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDTO {
     public static final Integer MAX_NAME_LENGTH = 100;
    public static final Integer MAX_DESCRIPTION_LENGTH = 500;

    private String name;
    private String description;
}
