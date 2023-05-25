package ch.cern.todo.mapping;

import ch.cern.todo.api.dto.TaskDTO;
import ch.cern.todo.dal.entity.TaskEntity;
import org.mapstruct.Mapper;

@Mapper(uses = CategoryMapper.class)
public interface TaskMapper extends DTOEntityMapper<TaskDTO, TaskEntity> {
}
