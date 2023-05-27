package ch.cern.todo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "constraints")
@Data
public class Config {
    private Integer maxTaskNameLength;
    private Integer maxTaskDescriptionLength;
    private Integer maxCategoryNameLength;
    private Integer maxCategoryDescriptionLength;

    @Value("${constraints.maxTaskNameLength}")
    public static final int MAX_TASK_NAME_LENGTH = 0;

    @Value("${constraints.maxTaskDescriptionLength}")
    public static final int MAX_TASK_DESCRIPTION_LENGTH = 0;


    @Value("${constraints.maxCategoryNameLength}")
    public static final int MAX_CATEGORY_NAME_LENGTH = 0;


    @Value("${constraints.maxCategoryDescriptionLength}")
    public static final int MAX_CATEGORY_DESCRIPTION_LENGTH = 0;
}
