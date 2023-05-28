package ch.cern.todo.bll.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class ResponseTaskDTO {
    @Getter
    @Setter
    private Long id;
    private final TaskDTO taskDTO = new TaskDTO();


    public String getName() {
        return taskDTO.getName();
    }

    public void setName(String name) {
        taskDTO.setName(name);
    }

    public String getDescription() {
        return taskDTO.getDescription();
    }

    public void setDescription(String description) {
        taskDTO.setDescription(description);
    }

    public LocalDate getDeadline() {
        return taskDTO.getDeadline();
    }

    public void setDeadline(LocalDate deadline) {
        taskDTO.setDeadline(deadline);
    }

    public String getCategoryName() {
        return taskDTO.getCategoryName();
    }

    public void setCategoryName(String categoryName) {
        taskDTO.setCategoryName(categoryName);
    }
}
