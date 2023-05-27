package ch.cern.todo.mapping;

import ch.cern.todo.bll.dto.ResponseTaskDTO;
import ch.cern.todo.dal.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CategoryResponseMapper.class)
public interface TaskResponseMapper extends GenericMapper<ResponseTaskDTO, TaskEntity> {
    @Mapping(source = "category.name", target = "categoryName")
    ResponseTaskDTO toDTO(TaskEntity entity);
}
