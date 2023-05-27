package ch.cern.todo.api.controller;

import ch.cern.todo.api.response.Response;
import ch.cern.todo.api.dto.CategoryDTO;
import ch.cern.todo.api.dto.ResponseCategoryDTO;
import ch.cern.todo.bll.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    Response<Collection<ResponseCategoryDTO>> getAll() {
        return Response.ok(categoryService.getAll());
    }

    @GetMapping("{id}")
    Response<ResponseCategoryDTO> getById(@PathVariable Long id) {
        return Response.ok(categoryService.getById(id));
    }

    @PostMapping
    Response<ResponseCategoryDTO> addCategory(@RequestBody CategoryDTO body) {
        return Response.ok(categoryService.add(body));
    }

    @PutMapping("{id}")
    Response<ResponseCategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO body) {

        return Response.ok(categoryService.update(body));
    }

    @DeleteMapping("{id}")
    Response<ResponseCategoryDTO> deleteById(@PathVariable Long id) {
        return Response.ok(categoryService.deleteById(id));
    }
}
