package ch.cern.todo.api.dto;


import lombok.Getter;
import lombok.Setter;

public class ResponseCategoryDTO {
    @Getter
    @Setter
    Long id;

    CategoryDTO categoryDTO;

    public String getName() {
        return categoryDTO.getName();
    }

    public void setName(String name) {
        categoryDTO.setName(name);
    }

    public String getDescription() {
        return categoryDTO.getDescription();
    }

    public void setDescription(String description) {
        categoryDTO.setDescription(description);
    }

}