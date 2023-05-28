package ch.cern.todo.bll.service;

import ch.cern.todo.bll.constants.EnglishStrings;
import ch.cern.todo.bll.dto.ResponseTaskDTO;
import ch.cern.todo.bll.dto.TaskDTO;
import ch.cern.todo.bll.exception.EntityAlreadyExistsException;
import ch.cern.todo.bll.exception.EntityNotFoundException;
import ch.cern.todo.bll.exception.InvalidDTOException;
import ch.cern.todo.bll.interfaces.TaskService;
import ch.cern.todo.config.Config;
import ch.cern.todo.dal.entity.CategoryEntity;
import ch.cern.todo.dal.entity.TaskEntity;
import ch.cern.todo.dal.repository.CategoryRepository;
import ch.cern.todo.dal.repository.TaskRepository;
import ch.cern.todo.mapping.TaskMapper;
import ch.cern.todo.mapping.TaskResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TaskResponseMapper taskResponseMapper;
    private final TaskMapper taskMapper;
    private final Config config;
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
        assertValid(taskDTO);
        return saveTask(taskDTO, e -> {});
    }


    @Transactional
    @Override
    public ResponseTaskDTO update(Long id, TaskDTO taskDTO) {
        // assert it is valid first, because that is faster, and only then assert it exists
        assertValid(taskDTO);
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

    private void assertValid(TaskDTO taskDTO){
        assertDescriptionLengthValid(taskDTO);
        assertNameLengthValid(taskDTO);
        assertDeadlineNotNull(taskDTO);
    }

    private void assertDeadlineNotNull(TaskDTO taskDTO) {
        if (taskDTO.getDeadline() == null)
            throw new InvalidDTOException(EnglishStrings.DEADLINE_IS_NULL.getValue());
    }

    private void assertNameLengthValid(TaskDTO taskDTO) {
        var nameLength = taskDTO.getName().length();
        if (nameLength > config.getMaxTaskNameLength())
            throw new InvalidDTOException(
                EnglishStrings.TOO_LONG_TASK_NAME
                    .formatted(nameLength, config.getMaxTaskNameLength()));
    }

    private void assertDescriptionLengthValid(TaskDTO taskDTO) {
        var descriptionLength = taskDTO.getDescription().length();
        if (descriptionLength > config.getMaxTaskDescriptionLength())
            throw new InvalidDTOException(
                EnglishStrings.TOO_LONG_TASK_DESCRIPTION
                    .formatted(descriptionLength,config.getMaxTaskDescriptionLength()));
    }

    private ResponseTaskDTO saveTask(TaskDTO taskDTO, Consumer<TaskEntity> actionOnEntityBeforeSave) {
        var categoryEntity = findCategoryOrThrow(taskDTO.getCategoryName());
        var mappedEntity = taskMapper.toEntity(taskDTO);

        mappedEntity.setCategory(categoryEntity);
        actionOnEntityBeforeSave.accept(mappedEntity);

        TaskEntity savedEntity = taskRepository.save(mappedEntity);
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
                .orElseThrow(() ->
                        new EntityNotFoundException(EnglishStrings.NO_CATEGORY.formatted(name)));
    }
}
