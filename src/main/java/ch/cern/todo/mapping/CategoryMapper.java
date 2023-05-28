package ch.cern.todo.mapping;

import ch.cern.todo.bll.dto.CategoryDTO;
import ch.cern.todo.dal.entity.CategoryEntity;
import org.mapstruct.Mapper;

//@Mapper
@Mapper
public interface CategoryMapper extends GenericMapper<CategoryDTO,CategoryEntity> {
}