package ch.cern.todo.bll.service;

import ch.cern.todo.api.dto.CategoryDTO;
import ch.cern.todo.api.dto.ResponseCategoryDTO;
import ch.cern.todo.api.dto.ResponseTaskDTO;
import ch.cern.todo.api.dto.TaskDTO;
import ch.cern.todo.bll.exception.EntityNotFoundException;
import ch.cern.todo.bll.interfaces.CategoryService;
import ch.cern.todo.dal.entity.CategoryEntity;
import ch.cern.todo.dal.entity.TaskEntity;
import ch.cern.todo.dal.repository.CategoryRepository;
import ch.cern.todo.mapping.CategoryMapper;
import ch.cern.todo.mapping.CategoryResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private final CategoryResponseMapper responseMapper;

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
        return saveCategory(categoryDTO,e -> {});
    }

    @Transactional
    @Override
    public ResponseCategoryDTO update(Long id, CategoryDTO categoryDTO) {
        assertExists(id);
        return saveCategory(categoryDTO,e -> e.setId(id));
    }

    @Transactional
    @Override
    public ResponseCategoryDTO deleteById(Long id) {
        var entity = findOrThrow(id);

        repository.deleteById(id);

        return responseMapper.toDTO(entity);
    }
    private void assertExists(Long id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException(CategoryEntity.class, id);
    }
    private ResponseCategoryDTO saveCategory(CategoryDTO categoryDTO, Consumer<CategoryEntity> actionOnEntityBeforeSave) {
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
