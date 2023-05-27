package ch.cern.todo.bll.interfaces;

import ch.cern.todo.bll.dto.CategoryDTO;
import ch.cern.todo.bll.dto.ResponseCategoryDTO;

import java.util.Collection;
public interface CategoryService {

    Collection<ResponseCategoryDTO> getAll();

    ResponseCategoryDTO getById(Long id);

    ResponseCategoryDTO add(CategoryDTO categoryDTO);

    ResponseCategoryDTO update(Long id, CategoryDTO categoryDTO);

    ResponseCategoryDTO deleteById(Long id);
}
