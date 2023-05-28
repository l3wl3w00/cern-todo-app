package ch.cern.todo;

import ch.cern.todo.config.Config;
import ch.cern.todo.mapping.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

@ComponentScan(basePackages = {"ch.cern.todo.mapping","ch.cern.todo"})
@EnableConfigurationProperties(Config.class)
@SpringBootApplication
public class TodoApplication {
	public static void main(String[] args) {

		SpringApplication.run(TodoApplication.class, args);
	}

	@Primary
	@Bean
	public CategoryMapper createCategoryMapper() {
		return new CategoryMapperImpl();
	}

	@Primary
	@Bean
	public CategoryResponseMapper createCategoryResponseMapper() {
		return new CategoryResponseMapperImpl();
	}

	@Primary
	@Bean
	public TaskResponseMapper createTaskResponseMapper() {
		return new TaskResponseMapperImpl();
	}

	@Primary
	@Bean
	public TaskMapper createTaskMapper() {
		return new TaskMapperImpl();
	}
}

