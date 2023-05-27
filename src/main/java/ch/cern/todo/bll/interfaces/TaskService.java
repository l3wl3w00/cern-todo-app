package ch.cern.todo.bll.interfaces;

import ch.cern.todo.api.dto.*;

import java.util.Collection;

public interface TaskService {

    Collection<ResponseTaskDTO> getAll();

    ResponseTaskDTO getById(Long id);

    ResponseTaskDTO add(TaskDTO categoryDTO);

    ResponseTaskDTO update(Long id, TaskDTO categoryDTO);

    ResponseTaskDTO deleteById(Long id);
}
