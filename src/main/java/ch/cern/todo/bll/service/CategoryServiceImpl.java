package ch.cern.todo.bll.service;

import ch.cern.todo.api.dto.CategoryDTO;
import ch.cern.todo.api.dto.ResponseCategoryDTO;
import ch.cern.todo.bll.exception.NoSuchEntityException;
import ch.cern.todo.bll.interfaces.CategoryService;
import ch.cern.todo.dal.entity.CategoryEntity;
import ch.cern.todo.dal.repository.CategoryRepository;
import ch.cern.todo.mapping.CategoryMapper;
import ch.cern.todo.mapping.CategoryResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

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
        var mappedEntity = mapper.toEntity(categoryDTO);

        var savedEntity = repository.save(mappedEntity);

        return responseMapper.toDTO(savedEntity);
    }

    @Override
    public ResponseCategoryDTO update(Long id, CategoryDTO categoryDTO) {
        var mappedEntity = mapper.toEntity(categoryDTO);

        mappedEntity.setId(id);

        var savedEntity = repository.save(mappedEntity);

        return responseMapper.toDTO(savedEntity);
    }

    @Override
    @Transactional
    public ResponseCategoryDTO deleteById(Long id) {
        var entity = findOrThrow(id);

        repository.deleteById(id);

        return responseMapper.toDTO(entity);
    }

    private CategoryEntity findOrThrow(Long id) {
        return repository
            .findById(id)
            .orElseThrow(() ->
                    new NoSuchEntityException(CategoryEntity.class,id));
    }
}
