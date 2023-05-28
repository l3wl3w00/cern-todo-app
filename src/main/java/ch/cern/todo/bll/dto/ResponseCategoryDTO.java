package ch.cern.todo.bll.dto;


import lombok.Getter;
import lombok.Setter;

public class ResponseCategoryDTO {
    @Getter
    @Setter
    private Long id;

    private final CategoryDTO categoryDTO = new CategoryDTO();

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