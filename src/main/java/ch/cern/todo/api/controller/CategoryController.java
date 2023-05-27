package ch.cern.todo.api.controller;

import ch.cern.todo.api.response.ContentResponse;
import ch.cern.todo.api.dto.CategoryDTO;
import ch.cern.todo.api.dto.ResponseCategoryDTO;
import ch.cern.todo.api.response.Response;
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
    Response getAll() {
        return ContentResponse.ok(categoryService.getAll());
    }

    @GetMapping("{id}")
    Response getById(@PathVariable Long id) {
        return ContentResponse.ok(categoryService.getById(id));
    }

    @PostMapping
    Response addCategory(@RequestBody CategoryDTO body) {
        return ContentResponse.created(categoryService.add(body));
    }

    @PutMapping("{id}")
    Response update(@PathVariable Long id, @RequestBody CategoryDTO body) {

        return ContentResponse.ok(categoryService.update(id, body));
    }

    @DeleteMapping("{id}")
    Response deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return Response.noContent();
    }
}
