package ch.cern.todo.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class TaskDTO {
    private String name;
    private String description;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate deadline;
    private String categoryName;
}
