package ch.cern.todo.bll.service;

import ch.cern.todo.bll.dto.CategoryDTO;
import ch.cern.todo.bll.dto.ResponseCategoryDTO;
import ch.cern.todo.bll.exception.EntityAlreadyExistsException;
import ch.cern.todo.bll.exception.EntityNotFoundException;
import ch.cern.todo.bll.exception.InvalidDTOException;
import ch.cern.todo.config.Config;
import ch.cern.todo.dal.entity.CategoryEntity;
import ch.cern.todo.dal.repository.CategoryRepository;
import ch.cern.todo.mapping.CategoryMapper;
import ch.cern.todo.mapping.CategoryResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceImplTest {
    CategoryRepository repository;
    CategoryMapper categoryMapper;
    CategoryResponseMapper categoryResponseMapper;

    CategoryServiceImpl categoryService;

    Config config;

    @BeforeEach
    void setUp() {
        setupConfig();
        createMocks();

        categoryService = new CategoryServiceImpl(
                repository,
                categoryMapper,
                categoryResponseMapper,
                config
        );
    }

    private void createMocks() {
        repository = mock(CategoryRepository.class);
        categoryMapper = mock(CategoryMapper.class);
        categoryResponseMapper = mock(CategoryResponseMapper.class);
    }

    private void setupConfig(){
        config = new Config();
        config.setMaxCategoryNameLength(100);
        config.setMaxTaskNameLength(100);

        config.setMaxCategoryDescriptionLength(500);
        config.setMaxTaskDescriptionLength(500);
    }
    @Test
    void getAll_ShouldReturnListOfResponseCategoryDTOs() {
        // Arrange
        CategoryEntity categoryEntity1 = createCategoryEntity(1L);
        CategoryEntity categoryEntity2 = createCategoryEntity(2L);
        when(repository.findAll()).thenReturn(Arrays.asList(categoryEntity1, categoryEntity2));
        ResponseCategoryDTO responseCategoryDTO1 = createResponseCategoryDTO(1L);
        ResponseCategoryDTO responseCategoryDTO2 = createResponseCategoryDTO(2L);
        when(categoryResponseMapper.toDTOCollection(anyCollection()))
                .thenReturn(Arrays.asList(responseCategoryDTO1, responseCategoryDTO2));

        // Act
        Collection<ResponseCategoryDTO> result = categoryService.getAll();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getId() == 1L));
        assertTrue(result.stream().anyMatch(dto -> dto.getId() == 2L));
    }

    @Test
    void getById_WithValidId_ShouldReturnResponseCategoryDTO() {
        // Arrange
        Long categoryId = 1L;
        CategoryEntity categoryEntity = createCategoryEntity(categoryId);
        when(repository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
        ResponseCategoryDTO responseCategoryDTO = createResponseCategoryDTO(categoryId);
        when(categoryResponseMapper.toDTO(any())).thenReturn(responseCategoryDTO);

        // Act
        ResponseCategoryDTO result = categoryService.getById(categoryId);

        // Assert
        assertEquals(categoryId, result.getId());
    }

    @Test
    void getById_WithInvalidId_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long categoryId = 1L;
        when(repository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> categoryService.getById(categoryId));
    }

    @Test
    void add_WithValidCategoryDTO_ShouldReturnResponseCategoryDTO() {
        // Arrange
        CategoryDTO categoryDTO = createCategoryDTO("TestCategory", "Test Description");
        CategoryEntity mappedEntity = createCategoryEntity(1L);
        when(categoryMapper.toEntity(categoryDTO)).thenReturn(mappedEntity);
        ResponseCategoryDTO savedDTO = createResponseCategoryDTO(1L);
        when(categoryResponseMapper.toDTO(any())).thenReturn(savedDTO);

        // Act
        ResponseCategoryDTO result = categoryService.add(categoryDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void add_WithInvalidCategoryDTO_ShouldThrowInvalidDTOException() {
        // Arrange
        CategoryDTO categoryDTO = createCategoryDTO("InvalidName", "");
        config.setMaxCategoryNameLength(10);
        // Act & Assert
        assertThrows(InvalidDTOException.class, () -> categoryService.add(categoryDTO));
    }

    @Test
    void add_WithExistingCategoryName_ShouldThrowEntityAlreadyExistsException() {
        // Arrange
        CategoryDTO categoryDTO = createCategoryDTO("TestCategory", "Test Description");
        CategoryEntity mappedEntity = createCategoryEntity(1L);
        when(categoryMapper.toEntity(categoryDTO)).thenReturn(mappedEntity);
        doThrow(new DataIntegrityViolationException("")).when(repository).save(mappedEntity);

        // Act & Assert
        assertThrows(EntityAlreadyExistsException.class, () -> categoryService.add(categoryDTO));
    }

    @Test
    void update_WithValidIdAndCategoryDTO_ShouldReturnResponseCategoryDTO() {
        // Arrange
        Long categoryId = 1L;
        CategoryDTO categoryDTO = createCategoryDTO("TestCategory", "Test Description");
        CategoryEntity categoryEntity = createCategoryEntity(categoryId);
        when(repository.existsById(categoryId)).thenReturn(true);
        when(categoryMapper.toEntity(categoryDTO)).thenReturn(categoryEntity);
        ResponseCategoryDTO savedDTO = createResponseCategoryDTO(categoryId);
        when(categoryResponseMapper.toDTO(any())).thenReturn(savedDTO);

        // Act
        ResponseCategoryDTO result = categoryService.update(categoryId, categoryDTO);

        // Assert
        assertNotNull(result);
        assertEquals(categoryId, result.getId());
    }

    @Test
    void update_WithInvalidId_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long categoryId = 1L;
        CategoryDTO categoryDTO = createCategoryDTO("TestCategory", "Test Description");
        when(repository.existsById(categoryId)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> categoryService.update(categoryId, categoryDTO));
    }

    @Test
    void update_WithInvalidCategoryDTO_ShouldThrowInvalidDTOException() {
        // Arrange
        Long categoryId = 1L;
        CategoryDTO categoryDTO = createCategoryDTO("InvalidName", "");
        when(repository.existsById(categoryId)).thenReturn(true);
        config.setMaxCategoryNameLength(10);
        // Act & Assert
        assertThrows(InvalidDTOException.class, () -> categoryService.update(categoryId, categoryDTO));
    }

    @Test
    void deleteById_WithValidId_ShouldReturnResponseCategoryDTO() {
        // Arrange
        Long categoryId = 1L;
        CategoryEntity categoryEntity = createCategoryEntity(categoryId);
        when(repository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
        ResponseCategoryDTO responseCategoryDTO = createResponseCategoryDTO(categoryId);
        when(categoryResponseMapper.toDTO(categoryEntity)).thenReturn(responseCategoryDTO);

        // Act
        ResponseCategoryDTO result = categoryService.deleteById(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(categoryId, result.getId());
        verify(repository, times(1)).deleteById(categoryId);
    }

    @Test
    void deleteById_WithInvalidId_ShouldThrowEntityNotFoundException() {
        // Arrange
        Long categoryId = 1L;
        when(repository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> categoryService.deleteById(categoryId));
        verify(repository, never()).deleteById(anyLong());
    }

    private CategoryEntity createCategoryEntity(Long id) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(id);
        categoryEntity.setName("TestCategory");
        categoryEntity.setDescription("Test Description");
        return categoryEntity;
    }

    private ResponseCategoryDTO createResponseCategoryDTO(Long id) {
        ResponseCategoryDTO responseCategoryDTO = new ResponseCategoryDTO();
        responseCategoryDTO.setId(id);
        responseCategoryDTO.setName("TestCategory");
        responseCategoryDTO.setDescription("Test Description");
        return responseCategoryDTO;
    }

    private CategoryDTO createCategoryDTO(String name, String description) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(name);
        categoryDTO.setDescription(description);
        return categoryDTO;
    }
}
