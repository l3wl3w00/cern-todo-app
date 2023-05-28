package ch.cern.todo.bll.constants;

import lombok.Getter;

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

    ;
    EnglishStrings(String value){
        this.value = value;
    }
    @Getter
    private final String value;

}
