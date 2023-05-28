package ch.cern.todo.bll.interfaces;

import ch.cern.todo.bll.dto.ResponseTaskDTO;
import ch.cern.todo.bll.dto.TaskDTO;

import java.util.Collection;

public interface TaskService {

    Collection<ResponseTaskDTO> getAll();

    ResponseTaskDTO getById(Long id);

    ResponseTaskDTO add(TaskDTO categoryDTO);

    ResponseTaskDTO update(Long id, TaskDTO categoryDTO);

    ResponseTaskDTO deleteById(Long id);
}
