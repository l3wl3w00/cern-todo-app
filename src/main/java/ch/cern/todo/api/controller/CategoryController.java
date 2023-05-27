package ch.cern.todo.api.controller;

import ch.cern.todo.api.response.ResponseFactory;
import ch.cern.todo.api.dto.CategoryDTO;
import ch.cern.todo.api.response.Response;
import ch.cern.todo.bll.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;
    private final ResponseFactory responseFactory;

    @GetMapping
    Response getAll() {
        return responseFactory.ok(categoryService::getAll);
    }

    @GetMapping("{id}")
    Response getById(@PathVariable Long id) {
        return responseFactory.okOrNotFound(() -> categoryService.getById(id));
    }

    @PostMapping
    Response add(@RequestBody CategoryDTO body) {
        return responseFactory.createdOrNotFound(() -> categoryService.add(body));
    }

    @PutMapping("{id}")
    Response update(@PathVariable Long id, @RequestBody CategoryDTO body) {
        return responseFactory.okOrNotFound(() -> categoryService.update(id, body));
    }

    @DeleteMapping("{id}")
    Response deleteById(@PathVariable Long id) {
        return responseFactory.deletedOrNotFound(() -> categoryService.deleteById(id));
    }
}
