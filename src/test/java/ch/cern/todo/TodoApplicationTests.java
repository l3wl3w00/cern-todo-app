package ch.cern.todo;

import ch.cern.todo.bll.dto.CategoryDTO;
import ch.cern.todo.bll.dto.ResponseCategoryDTO;
import ch.cern.todo.bll.dto.ResponseTaskDTO;
import ch.cern.todo.bll.dto.TaskDTO;
import ch.cern.todo.dal.entity.CategoryEntity;
import ch.cern.todo.dal.entity.TaskEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class TodoApplicationTests {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	JsonPathResultMatchers statusNameMatcher;
	ArrayList<CategoryEntity> categoryEntities;
	ArrayList<TaskEntity> taskEntities;

	@BeforeEach
	public void setUp(){
		categoryEntities = new ArrayList<>();
		taskEntities = new ArrayList<>();

		createCategories(10);
		createTasks(10);

		statusNameMatcher = MockMvcResultMatchers.jsonPath("$.statusName");

	}

	void createTasks(int howMuch) {
		for (int i = howMuch; i > 0; i--) {
			TaskEntity task = new TaskEntity();

			int index = howMuch - i;
			task.setName("Task " + index);
			task.setDescription("Task Description " + index);

			task.setCategory(categoryEntities.get(i - 1));
			task.setDeadline(LocalDate.ofYearDay(2023 + i, 5 * i));

			entityManager.persist(task);
			taskEntities.add(task);
		}
	}
	void createCategories(int howMuch){

		for (int i = 0; i < howMuch; i++) {
			CategoryEntity category = new CategoryEntity();

			category.setDescription("Category Description " + i);
			category.setName("Category " + (i + 1));
			entityManager.persist(category);
			categoryEntities.add(category);
		}
	}

	@Test
	public void getAllCategories_Success() throws Exception {

		JsonPathResultMatchers contentMatcher = MockMvcResultMatchers.jsonPath("$.content");

		var listLengthMatcher = this.<Collection<ResponseCategoryDTO>>createMatcher(l -> l.size() == 10);
		var nameMatcher = this.<Collection<LinkedHashMap<String,?>>>createMatcher(l ->
				l.stream().anyMatch( r -> r.get("name").equals("Category 5") ));

		mockMvc.perform(get("/category"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(statusNameMatcher.value("OK"))
				.andExpect(contentMatcher.isArray())
				.andExpect(contentMatcher.value(listLengthMatcher))
				.andExpect(contentMatcher.value(nameMatcher))
		;
	}

	@Test
	public void getCategoryById_Success() throws Exception {
		Long categoryId = categoryEntities.get(0).getId();
		mockMvc.perform(get("/category/{id}", categoryId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.id").value(categoryId));
	}
	@Test
	public void getCategoryById_DoesntExist() throws Exception {
		Long categoryId = -100L;
		mockMvc.perform(get("/category/{id}", categoryId))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(statusNameMatcher.value("Not Found"));
	}

	@Test
	public void addCategory_Success() throws Exception {
		CategoryDTO category = new CategoryDTO();
		category.setName("New Category");

		performAndWrite(() -> post("/category"), category)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(statusNameMatcher.value("Created"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.name").value(category.getName()));
	}

	@Test
	public void addCategory_CategoryAlreadyExists() throws Exception {
		// Will produce false negative results because @Transactional is buggy with MockMVC, so can't implement this here
	}

	@Test
	public void updateCategory_Success() throws Exception {
		Long categoryId = categoryEntities.get(0).getId();
		CategoryDTO category = new CategoryDTO();
		category.setName("Updated Category");

		performAndWrite(() -> put("/category/{id}", categoryId), category)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.name").value(category.getName()));
	}

	@Test
	public void updateCategory_CategoryAlreadyExists() throws Exception {
		// Will produce false negative results because @Transactional is buggy with MockMVC, so can't implement this here
	}


	@Test
	public void deleteCategory_Success() throws Exception {
		Long categoryId = categoryEntities.get(0).getId();
		mockMvc.perform(delete("/category/{id}", categoryId))
				.andExpect(MockMvcResultMatchers.status().isNoContent())
				.andExpect(statusNameMatcher.value("No Content"));
	}

	@Test
	public void deleteCategory_NotFound() throws Exception {
		Long categoryId = -100L;
		mockMvc.perform(delete("/category/{id}", categoryId))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(statusNameMatcher.value("Not Found"));
	}



	@Test
	public void getAllTasks_Success() throws Exception {

		JsonPathResultMatchers contentMatcher = MockMvcResultMatchers.jsonPath("$.content");

		var listLengthMatcher = this.<Collection<ResponseTaskDTO>>createMatcher(l -> l.size() == 10);
		var nameMatcher = this.<Collection<LinkedHashMap<String,?>>>createMatcher(l ->
				l.stream().anyMatch( r -> r.get("name").equals("Task 5") ));

		mockMvc.perform(get("/task"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(statusNameMatcher.value("OK"))
				.andExpect(contentMatcher.isArray())
				.andExpect(contentMatcher.value(listLengthMatcher))
				.andExpect(contentMatcher.value(nameMatcher))
		;
	}

	@Test
	public void getTaskById_Success() throws Exception {
		Long taskId = taskEntities.get(0).getId();
		mockMvc.perform(get("/task/{id}", taskId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.id").value(taskId));
	}
	@Test
	public void getTaskById_DoesntExist() throws Exception {
		Long categoryId = -100L;
		mockMvc.perform(get("/task/{id}", categoryId))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(statusNameMatcher.value("Not Found"));
	}

	@Test
	public void addTask_Success() throws Exception {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setName("New Task");
		taskDTO.setCategoryName(categoryEntities.get(0).getName());
		taskDTO.setDeadline(LocalDate.now());

		performAndWrite(() -> post("/task"), taskDTO)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(statusNameMatcher.value("Created"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.name").value(taskDTO.getName()));
	}

	@Test
	public void addTask_CategoryDoesntExist() throws Exception {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setName("New Task");
		taskDTO.setCategoryName("Nonexistent Category");
		taskDTO.setDeadline(LocalDate.now());

		performAndWrite(() -> post("/task"), taskDTO)
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(statusNameMatcher.value("Not Found"));
	}

	@Test
	public void updateTask_Success() throws Exception {
		Long taskId = taskEntities.get(0).getId();
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setName("Updated Task");
		taskDTO.setCategoryName(categoryEntities.get(0).getName());
		taskDTO.setDeadline(LocalDate.now());

		performAndWrite(() -> put("/task/{id}", taskId), taskDTO)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(statusNameMatcher.value("OK"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.name").value(taskDTO.getName()));
	}

	@Test
	public void updateTask_CategoryDoesntExist() throws Exception {
		Long taskId = taskEntities.get(0).getId();
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setDeadline(LocalDate.now());
		taskDTO.setName("Updated Task");
		taskDTO.setCategoryName("Nonexistent Category");

		performAndWrite(() -> put("/task/{id}", taskId), taskDTO)
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(statusNameMatcher.value("Not Found"));
	}


	@Test
	public void deleteTask_Success() throws Exception {
		Long taskId = taskEntities.get(0).getId();
		mockMvc.perform(delete("/task/{id}", taskId))
				.andExpect(MockMvcResultMatchers.status().isNoContent())
				.andExpect(statusNameMatcher.value("No Content"));
		// should not be in db after deleted
		var deletedEntity = entityManager
				.createQuery("select e from CategoryEntity e where e.id = :id")
				.setParameter("id",taskId)
				.getResultList();
		Assertions.assertTrue(deletedEntity.isEmpty());
	}

	@Test
	public void deleteTask_NotFound() throws Exception {
		Long taskId = -100L;
		mockMvc.perform(delete("/task/{id}", taskId))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(statusNameMatcher.value("Not Found"));
	}


	private <DTO> ResultActions performAndWrite(Supplier<MockHttpServletRequestBuilder> action, DTO dto) throws Exception {
		return mockMvc.perform(action.get()
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)));
	}

	<T> Matcher<T> createMatcher(Predicate<T> predicate)
	{
		return new BaseMatcher<T>() {
			@Override
			public boolean matches(Object actual) {
				return predicate.test((T)actual);
			}

			@Override
			public void describeTo(Description description) {

			}
		};
	}
}
