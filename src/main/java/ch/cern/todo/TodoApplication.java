package ch.cern.todo;

import ch.cern.todo.mapping.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

@ComponentScan(basePackages = {"ch.cern.todo.mapping","ch.cern.todo"})
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
	public TaskMapper createTaskMapper() {
		return new TaskMapperImpl();
	}
}

