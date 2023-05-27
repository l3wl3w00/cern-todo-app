package ch.cern.todo.mapping;

import ch.cern.todo.api.dto.ResponseTaskDTO;
import ch.cern.todo.api.dto.TaskDTO;
import ch.cern.todo.dal.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(uses = CategoryMapper.class)
public interface TaskMapper extends GenericMapper<TaskDTO, TaskEntity> {
    @Mapping(source = "category.name", target = "categoryName")
    TaskDTO toDTO(TaskEntity entity);
}