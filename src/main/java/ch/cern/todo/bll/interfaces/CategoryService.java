package ch.cern.todo.bll.interfaces;

import ch.cern.todo.api.dto.CategoryDTO;
import ch.cern.todo.api.dto.ResponseCategoryDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;
public interface CategoryService {

    Collection<ResponseCategoryDTO> getAll();

    ResponseCategoryDTO getById(Long id);

    ResponseCategoryDTO add(CategoryDTO categoryDTO);

    ResponseCategoryDTO update(CategoryDTO categoryDTO);

    ResponseCategoryDTO deleteById(Long id);
}
