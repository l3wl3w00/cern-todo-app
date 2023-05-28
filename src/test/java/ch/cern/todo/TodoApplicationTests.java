package ch.cern.todo;

import ch.cern.todo.bll.dto.CategoryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class TodoApplicationTests {


	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	@Test
	public void testGetAllCategories() throws Exception {
		mockMvc.perform(get("/category"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray());
	}

	@Test
	public void testGetCategoryById() throws Exception {
		Long categoryId = 1L;
		ResultActions result = mockMvc.perform(get("/category/{id}", categoryId));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(categoryId));
	}

	@Test
	public void testAddCategory() throws Exception {
		CategoryDTO category = new CategoryDTO();
		category.setName("New Category");

		ResultActions result = mockMvc.perform(post("/category")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(category)));

		result.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(category.getName()));
	}

	@Test
	public void testUpdateCategory() throws Exception {
		Long categoryId = 1L;
		CategoryDTO category = new CategoryDTO();
		category.setName("Updated Category");

		ResultActions result = mockMvc.perform(put("/category/{id}", categoryId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(category)));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(categoryId))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(category.getName()));
	}

	@Test
	public void testDeleteCategory() throws Exception {
		Long categoryId = 1L;
		ResultActions result = mockMvc.perform(delete("/category/{id}", categoryId));

		result.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

}
