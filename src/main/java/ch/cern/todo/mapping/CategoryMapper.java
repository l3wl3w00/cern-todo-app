package ch.cern.todo.mapping;

import ch.cern.todo.api.dto.CategoryDTO;
import ch.cern.todo.dal.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper extends DTOEntityMapper<CategoryDTO,CategoryEntity> {
}