package ch.cern.todo.mapping;

import ch.cern.todo.api.dto.CategoryDTO;
import ch.cern.todo.dal.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.Collection;

//@Mapper
@Mapper
public interface CategoryMapper extends GenericMapper<CategoryDTO,CategoryEntity> {
}