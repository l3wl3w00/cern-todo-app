package ch.cern.todo.bll.service;

import ch.cern.todo.bll.constants.EnglishStrings;
import ch.cern.todo.bll.dto.CategoryDTO;
import ch.cern.todo.bll.dto.ResponseCategoryDTO;
import ch.cern.todo.bll.dto.TaskDTO;
import ch.cern.todo.bll.exception.EntityAlreadyExistsException;
import ch.cern.todo.bll.exception.EntityNotFoundException;
import ch.cern.todo.bll.exception.InvalidDTOException;
import ch.cern.todo.bll.interfaces.CategoryService;
import ch.cern.todo.config.Config;
import ch.cern.todo.dal.entity.CategoryEntity;
import ch.cern.todo.dal.repository.CategoryRepository;
import ch.cern.todo.mapping.CategoryMapper;
import ch.cern.todo.mapping.CategoryResponseMapper;
import lombok.RequiredArgsConstructor;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.hibernate.JDBCException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.function.Consumer;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final CategoryResponseMapper responseMapper;
    private final Config config;

    @Override
    public Collection<ResponseCategoryDTO> getAll() {
        return responseMapper.toDTOCollection(repository.findAll());
    }

    @Override
    public ResponseCategoryDTO getById(Long id) {
        return responseMapper.toDTO(findOrThrow(id));
    }

    @Override
    public ResponseCategoryDTO add(CategoryDTO categoryDTO) {
        assertValid(categoryDTO);
        return trySave(categoryDTO,e -> {});
    }

    @Transactional
    @Override
    public ResponseCategoryDTO update(Long id, CategoryDTO categoryDTO) {
        // assert it is valid first, because that is faster, and only then assert it exists
        assertValid(categoryDTO);
        assertExists(id);
        return trySave(categoryDTO, e -> e.setId(id));
    }

    @Transactional
    @Override
    public ResponseCategoryDTO deleteById(Long id) {
        var entity = findOrThrow(id);

        repository.deleteById(id);

        return responseMapper.toDTO(entity);
    }

    private ResponseCategoryDTO trySave(CategoryDTO categoryDTO, Consumer<CategoryEntity> actionOnEntityBeforeSave) {
        try {
            return saveCategory(categoryDTO, actionOnEntityBeforeSave);
        } catch (DataIntegrityViolationException e) {
            var message = String.format(EnglishStrings.CATEGORY_WITH_NAME_EXITS.getValue(), categoryDTO.getName());
            throw new EntityAlreadyExistsException(message);
        }
    }

    private void assertValid(CategoryDTO categoryDTO){
        assertDescriptionLengthValid(categoryDTO);
        assertNameLengthValid(categoryDTO);
    }

    private void assertNameLengthValid(CategoryDTO taskDTO) {
        var nameLength = taskDTO.getName().length();
        if (nameLength > config.getMaxCategoryNameLength())
            throw new InvalidDTOException(
                    String.format(EnglishStrings.TOO_LONG_CATEGORY_NAME.getValue(), nameLength, config.getMaxCategoryNameLength()));
    }

    private void assertDescriptionLengthValid(CategoryDTO taskDTO) {
        var descriptionLength = taskDTO.getDescription().length();
        if (descriptionLength > config.getMaxCategoryDescriptionLength())
            throw new InvalidDTOException(
                    String.format(EnglishStrings.TOO_LONG_CATEGORY_DESCRIPTION.getValue(), descriptionLength, config.getMaxCategoryDescriptionLength()));
    }
    private void assertExists(Long id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException(CategoryEntity.class, id);
    }
    private ResponseCategoryDTO saveCategory(CategoryDTO categoryDTO, Consumer<CategoryEntity> actionOnEntityBeforeSave)
            throws DataIntegrityViolationException {
        var mappedEntity = mapper.toEntity(categoryDTO);
        actionOnEntityBeforeSave.accept(mappedEntity);
        var savedEntity = repository.save(mappedEntity);
        return responseMapper.toDTO(savedEntity);
    }
    private CategoryEntity findOrThrow(Long id) {
        return repository
            .findById(id)
            .orElseThrow(() ->
                    new EntityNotFoundException(CategoryEntity.class,id));
    }
}
