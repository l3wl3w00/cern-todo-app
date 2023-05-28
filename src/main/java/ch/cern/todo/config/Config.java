package ch.cern.todo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "constraints")
@Data
public class Config {
    private Integer maxTaskNameLength;
    private Integer maxTaskDescriptionLength;
    private Integer maxCategoryNameLength;
    private Integer maxCategoryDescriptionLength;
}
