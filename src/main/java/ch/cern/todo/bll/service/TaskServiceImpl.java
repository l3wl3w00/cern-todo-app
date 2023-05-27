package ch.cern.todo.bll.service;

import ch.cern.todo.api.dto.ResponseTaskDTO;
import ch.cern.todo.api.dto.TaskDTO;
import ch.cern.todo.bll.exception.CategoryNotFoundException;
import ch.cern.todo.bll.exception.EntityNotFoundException;
import ch.cern.todo.bll.interfaces.TaskService;
import ch.cern.todo.dal.entity.CategoryEntity;
import ch.cern.todo.dal.entity.TaskEntity;
import ch.cern.todo.dal.repository.CategoryRepository;
import ch.cern.todo.dal.repository.TaskRepository;
import ch.cern.todo.mapping.TaskMapper;
import ch.cern.todo.mapping.TaskResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TaskResponseMapper taskResponseMapper;
    private final TaskMapper taskMapper;

    @Override
    public Collection<ResponseTaskDTO> getAll() {
        return taskResponseMapper.toDTOCollection(taskRepository.findAll());
    }

    @Override
    public ResponseTaskDTO getById(Long id) {
        return taskResponseMapper.toDTO(findTaskOrThrow(id));
    }

    @Transactional
    @Override
    public ResponseTaskDTO add(TaskDTO taskDTO) {
        return saveTask(taskDTO, e -> {});
    }


    @Transactional
    @Override
    public ResponseTaskDTO update(Long id, TaskDTO taskDTO) {
        assertExists(id);
        return saveTask(taskDTO, e -> e.setId(id));
    }


    @Override
    public ResponseTaskDTO deleteById(Long id) {
        var entity = findTaskOrThrow(id);

        taskRepository.deleteById(id);

        return taskResponseMapper.toDTO(entity);
    }

    private void assertExists(Long id) {
        if (!taskRepository.existsById(id))
            throw new EntityNotFoundException(TaskEntity.class, id);
    }

    private ResponseTaskDTO saveTask(TaskDTO taskDTO, Consumer<TaskEntity> actionOnEntityBeforeSave) {
        var categoryEntity = findCategoryOrThrow(taskDTO.getCategoryName());
        var mappedEntity = taskMapper.toEntity(taskDTO);

        mappedEntity.setCategory(categoryEntity);
        actionOnEntityBeforeSave.accept(mappedEntity);

        var savedEntity = taskRepository.save(mappedEntity);
        return taskResponseMapper.toDTO(savedEntity);
    }

    private TaskEntity findTaskOrThrow(Long id) {
        return taskRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(TaskEntity.class,id));
    }

    private CategoryEntity findCategoryOrThrow(String name) {
        return categoryRepository
                .findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException(name));
    }
}
