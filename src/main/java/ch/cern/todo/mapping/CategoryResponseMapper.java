package ch.cern.todo.mapping;

import ch.cern.todo.api.dto.CategoryDTO;
import ch.cern.todo.api.dto.ResponseCategoryDTO;
import ch.cern.todo.dal.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface CategoryResponseMapper extends GenericMapper<ResponseCategoryDTO, CategoryEntity> {
}
