package ch.cern.todo.bll.service;


import ch.cern.todo.bll.dto.ResponseTaskDTO;
import ch.cern.todo.bll.dto.TaskDTO;
import ch.cern.todo.bll.exception.EntityNotFoundException;
import ch.cern.todo.config.Config;
import ch.cern.todo.dal.entity.CategoryEntity;
import ch.cern.todo.dal.entity.TaskEntity;
import ch.cern.todo.dal.repository.CategoryRepository;
import ch.cern.todo.dal.repository.TaskRepository;
import ch.cern.todo.mapping.TaskMapper;
import ch.cern.todo.mapping.TaskResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskServiceImplTest {
    private TaskRepository taskRepository;
    private CategoryRepository categoryRepository;
    private TaskResponseMapper taskResponseMapper;
    private TaskMapper taskMapper;
    private Config config;

    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        setupConfig();
        createMocks();

        taskService = new TaskServiceImpl(
                taskRepository,
                categoryRepository,
                taskResponseMapper,
                taskMapper,
                config
        );
    }

    private void createMocks() {
        taskRepository = mock(TaskRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        taskMapper = mock(TaskMapper.class);
        taskResponseMapper = mock(TaskResponseMapper.class);
    }

    private void setupConfig(){
        config = new Config();
        config.setMaxCategoryNameLength(100);
        config.setMaxTaskNameLength(100);

        config.setMaxCategoryDescriptionLength(500);
        config.setMaxTaskDescriptionLength(500);
    }

    @Test
    void getAll_ShouldReturnAllTasks() {
        // Arrange
        TaskEntity task1 = createTaskEntity(1L, "Task 1", "Description 1");
        TaskEntity task2 = createTaskEntity(2L, "Task 2", "Description 2");
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));
        ResponseTaskDTO responseTask1 = createResponseTaskDTO(1L, "Task 1", "Description 1");
        ResponseTaskDTO responseTask2 = createResponseTaskDTO(2L, "Task 2", "Description 2");
        when(taskResponseMapper.toDTOCollection(Arrays.asList(task1, task2)))
                .thenReturn(Arrays.asList(responseTask1, responseTask2));

        // Act
        Collection<ResponseTaskDTO> result = taskService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(responseTask1));
        assertTrue(result.contains(responseTask2));
    }

    @Test
    void getById_WithValidId_ShouldReturnResponseTaskDTO() {
        // Arrange
        Long taskId = 1L;
        TaskEntity taskEntity = createTaskEntity(taskId, "Task 1", "Description 1");
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));
        ResponseTaskDTO responseTaskDTO = createResponseTaskDTO(taskId, "Task 1", "Description 1");
        when(taskResponseMapper.toDTO(taskEntity)).thenReturn(responseTaskDTO);

        // Act
        ResponseTaskDTO result = taskService.getById(taskId);

        // Assert
        assertNotNull(result);
        assertEquals(taskId, result.getId());
        assertEquals("Task 1", result.getName());
        assertEquals("Description 1", result.getDescription());
    }

    @Test
    void getById_WithInvalidId_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> taskService.getById(taskId));
    }

    @Test
    void add_WithValidTaskDTO_ShouldReturnResponseTaskDTO() {
        // Arrange
        Long taskId = 1L;
        CategoryEntity existingCategory = createCategoryEntity(taskId, "Category1");
        TaskDTO taskDTO = createTaskDTO("Task 1", "Description 1","");
        TaskEntity savedEntity = createTaskEntity(taskId, "Task 1", "Description 1");
        when(taskMapper.toEntity(taskDTO)).thenReturn(savedEntity);
        when(taskRepository.save(savedEntity)).thenReturn(savedEntity);
        when(categoryRepository.findByName(anyString()))
                .thenReturn(Optional.of(existingCategory));
        ResponseTaskDTO responseTaskDTO = createResponseTaskDTO(taskId, "Task 1", "Description 1");
        when(taskResponseMapper.toDTO(savedEntity)).thenReturn(responseTaskDTO);

        // Act
        ResponseTaskDTO result = taskService.add(taskDTO);

        // Assert
        assertNotNull(result);
        assertEquals(taskId, result.getId());
        assertEquals("Task 1", result.getName());
        assertEquals("Description 1", result.getDescription());
    }

    @Test
    void add_WithInvalidCategoryName_ShouldThrowEntityNotFoundException() {
        // Arrange
        TaskDTO taskDTO = createTaskDTO("Task 1", "Description 1", "Invalid Category");
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> taskService.add(taskDTO));
    }

    @Test
    void update_WithValidIdAndTaskDTO_ShouldReturnResponseTaskDTO() {
        // Arrange
        Long taskId = 1L;
        String categoryName = "Category 1";
        TaskDTO taskDTO = createTaskDTO("Task 1", "Description 1",categoryName);
        TaskEntity existingTask = createTaskEntity(taskId, "Existing Task", "Existing Description");
        CategoryEntity categoryEntity = createCategoryEntity(1L, categoryName);

        when(taskRepository.existsById(taskId)).thenReturn(true);
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(existingTask);
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(categoryEntity));

        ResponseTaskDTO responseTaskDTO = createResponseTaskDTO(taskId, "Existing Task", "Existing Description");
        when(taskResponseMapper.toDTO(existingTask)).thenReturn(responseTaskDTO);

        when(taskMapper.toEntity(any(TaskDTO.class))).thenReturn(existingTask);
        // Act
        ResponseTaskDTO result = taskService.update(taskId, taskDTO);

        // Assert
        assertNotNull(result);
        assertEquals(taskId, result.getId());
        assertEquals("Existing Task", result.getName());
        assertEquals("Existing Description", result.getDescription());
    }
    @Test
    void update_WithInvalidId_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long taskId = 1L;
        TaskDTO taskDTO = createTaskDTO("Task 1", "Description 1", "");
        when(taskRepository.existsById(taskId)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> taskService.update(taskId, taskDTO));
    }

    @Test
    void deleteById_WithValidId_ShouldReturnResponseTaskDTO() {
        // Arrange
        Long taskId = 1L;
        TaskEntity taskEntity = createTaskEntity(taskId, "Task 1", "Description 1");
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));
        doNothing().when(taskRepository).deleteById(taskId);
        ResponseTaskDTO responseTaskDTO = createResponseTaskDTO(taskId, "Task 1", "Description 1");
        when(taskResponseMapper.toDTO(taskEntity)).thenReturn(responseTaskDTO);

        // Act
        ResponseTaskDTO result = taskService.deleteById(taskId);

        // Assert
        assertNotNull(result);
        assertEquals(taskId, result.getId());
        assertEquals("Task 1", result.getName());
        assertEquals("Description 1", result.getDescription());
    }

    @Test
    void deleteById_WithInvalidId_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> taskService.deleteById(taskId));
    }

    private TaskEntity createTaskEntity(Long id, String name, String description) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(id);
        taskEntity.setName(name);
        taskEntity.setDescription(description);
        return taskEntity;
    }

    private ResponseTaskDTO createResponseTaskDTO(Long id, String name, String description) {
        ResponseTaskDTO responseTaskDTO = new ResponseTaskDTO();
        responseTaskDTO.setId(id);
        responseTaskDTO.setName(name);
        responseTaskDTO.setDescription(description);
        return responseTaskDTO;
    }

    private TaskDTO createTaskDTO(String name, String description, String categoryName) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setName(name);
        taskDTO.setDescription(description);
        taskDTO.setCategoryName(categoryName);
        return taskDTO;
    }
    private CategoryEntity createCategoryEntity(Long id, String name) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(id);
        categoryEntity.setName(name);
        // Set any other properties of the category entity as needed
        return categoryEntity;
    }
}