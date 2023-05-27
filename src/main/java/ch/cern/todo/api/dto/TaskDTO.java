package ch.cern.todo.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class TaskDTO {
    private String name;
    private String description;
    private LocalDate deadline;
    private CategoryDTO category;
}
