package ch.cern.todo.bll.constants;

import lombok.Getter;
import org.apache.el.ValueExpressionImpl;

public enum EnglishStrings {
    SHOULD_BE("It is %d characters, and it should be %d or less!"),

    TOO_LONG_TASK_DESCRIPTION("The description of the task is too long! " + SHOULD_BE),
    TOO_LONG_CATEGORY_DESCRIPTION("The description of the category is too long! " + SHOULD_BE),
    TOO_LONG_TASK_NAME("The name of the task is too long! " + SHOULD_BE),
    TOO_LONG_CATEGORY_NAME("The name of the category is too long! " + SHOULD_BE),
    DEADLINE_IS_NULL("The deadline of the task was not provided!"),

    ENTITY_WITH_ID_EXITS("Entity with %d id already exists"),
    CATEGORY_WITH_NAME_EXITS("Category with %s name already exists"),
    NO_ENTITY("No %s entity exists with id %d"),
    NO_CATEGORY("No category was found with name %s"),

    CONSTRAINT_VIOLATION("The requested operation cannot be performed due to a constraint violation"),
    INVALID_REQUEST("The request is invalid"),
    RESOURCE_DOESNT_EXIST("The resource you are trying to access does not exist"),
    RESOURCE_WAS_DELETED("The resource was deleted"),
    ;

    EnglishStrings(String value){
        this.value = value;
    }
    @Getter
    private final String value;

    public String formatted(Object... args) {
        return String.format(getValue(), args);
    }

}
