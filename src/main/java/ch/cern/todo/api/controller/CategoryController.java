package ch.cern.todo.api.controller;

import ch.cern.todo.api.response.ResponseFactory;
import ch.cern.todo.bll.dto.CategoryDTO;
import ch.cern.todo.api.response.Response;
import ch.cern.todo.bll.dto.NoContentDTO;
import ch.cern.todo.bll.dto.ResponseCategoryDTO;
import ch.cern.todo.bll.interfaces.CategoryService;
import ch.cern.todo.config.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;
    private final ResponseFactory responseFactory;
    private final Config config;
    @GetMapping
    Response<Collection<ResponseCategoryDTO>> getAll() {;
        return responseFactory.ok(categoryService::getAll);
    }

    @GetMapping("{id}")
    Response<ResponseCategoryDTO> getById(@PathVariable Long id) {
        return responseFactory.<ResponseCategoryDTO>okOrHandleError(() -> categoryService.getById(id));
    }

    @PostMapping
    Response<ResponseCategoryDTO> add(@RequestBody CategoryDTO body) {
        return responseFactory.createdOrHandleError(() -> categoryService.add(body));
    }

    @PutMapping("{id}")
    Response<ResponseCategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO body) {
        return responseFactory.okOrHandleError(() -> categoryService.update(id, body));
    }

    @DeleteMapping("{id}")
    Response<NoContentDTO> deleteById(@PathVariable Long id) {
        return responseFactory.deletedOrHandleError(() -> categoryService.deleteById(id));
    }
}
