package ch.cern.todo.mapping;

import ch.cern.todo.bll.dto.ResponseCategoryDTO;
import ch.cern.todo.dal.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryResponseMapper extends GenericMapper<ResponseCategoryDTO, CategoryEntity> {
}
