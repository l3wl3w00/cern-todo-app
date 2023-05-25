package ch.cern.todo.bll.service;

import ch.cern.todo.api.dto.CategoryDTO;
import ch.cern.todo.api.dto.TaskDTO;
import ch.cern.todo.bll.exception.NoSuchEntityException;
import ch.cern.todo.bll.interfaces.TaskService;
import ch.cern.todo.dal.entity.CategoryEntity;
import ch.cern.todo.dal.entity.TaskEntity;
import ch.cern.todo.dal.repository.CategoryRepository;
import ch.cern.todo.dal.repository.TaskRepository;
import ch.cern.todo.mapping.CategoryMapper;
import ch.cern.todo.mapping.TaskMapper;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TaskMapper taskMapper;
    private final CategoryMapper categoryMapper;

    @Override
    public Collection<TaskDTO> getAll() {
        return taskMapper.toDTOCollection(taskRepository.findAll());
    }

    @Override
    public TaskDTO getById(Long id) {
        return taskMapper.toDTO(findOrThrow(id));
    }

    @Override
    public TaskDTO add(TaskDTO taskDTO) {
        return addOrUpdate(taskDTO);
    }

    @Override
    public TaskDTO update(TaskDTO taskDTO) {
        return addOrUpdate(taskDTO);
    }

    @Override
    public TaskDTO deleteById(Long id) {
        var entity = findOrThrow(id);

        taskRepository.deleteById(id);

        return taskMapper.toDTO(entity);
    }

    private TaskDTO addOrUpdate(TaskDTO taskDTO){
        var mappedEntity = taskMapper.toEntity(taskDTO);

        var savedEntity = taskRepository.save(mappedEntity);

        return taskMapper.toDTO(savedEntity);
    }
    private TaskEntity findOrThrow(Long id) {
        return taskRepository
                .findById(id)
                .orElseThrow(() ->
                        new NoSuchEntityException(TaskEntity.class,id));
    }
}
