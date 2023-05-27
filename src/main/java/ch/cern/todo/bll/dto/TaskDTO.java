package ch.cern.todo.bll.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class TaskDTO {
    public static final Integer MAX_NAME_LENGTH = 100;
    public static final Integer MAX_DESCRIPTION_LENGTH = 500;

    private String name;
    private String description;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate deadline;
    private String categoryName;
}
