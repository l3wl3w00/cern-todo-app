package ch.cern.todo.bll.service;

import ch.cern.todo.api.dto.CategoryDTO;
import ch.cern.todo.bll.exception.NoSuchEntityException;
import ch.cern.todo.bll.interfaces.CategoryService;
import ch.cern.todo.dal.entity.CategoryEntity;
import ch.cern.todo.dal.repository.CategoryRepository;
import ch.cern.todo.mapping.CategoryMapper;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public Collection<CategoryDTO> getAll() {
        return mapper.toDTOCollection(repository.findAll());
    }

    @Override
    public CategoryDTO getById(Long id) {
        return mapper.toDTO(findOrThrow(id));
    }

    @Override
    public CategoryDTO add(CategoryDTO categoryDTO) {
        return addOrUpdate(categoryDTO);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        return addOrUpdate(categoryDTO);
    }

    @Override
    @Transactional
    public CategoryDTO deleteById(Long id) {
        var entity = findOrThrow(id);

        repository.deleteById(id);

        return mapper.toDTO(entity);
    }

    private CategoryDTO addOrUpdate(CategoryDTO categoryDTO){
        var mappedEntity = mapper.toEntity(categoryDTO);

        var savedEntity = repository.save(mappedEntity);

        return mapper.toDTO(savedEntity);
    }

    private CategoryEntity findOrThrow(Long id) {
        return repository
            .findById(id)
            .orElseThrow(() ->
                    new NoSuchEntityException(CategoryEntity.class,id));
    }
}
