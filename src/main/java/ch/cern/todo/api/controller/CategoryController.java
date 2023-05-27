package ch.cern.todo.api.controller;

import ch.cern.todo.api.response.ResponseFactory;
import ch.cern.todo.bll.dto.CategoryDTO;
import ch.cern.todo.api.response.Response;
import ch.cern.todo.bll.interfaces.CategoryService;
import ch.cern.todo.config.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;
    private final ResponseFactory responseFactory;
    private final Config config;
    @GetMapping
    Response getAll() {;
        return responseFactory.ok(categoryService::getAll);
    }

    @GetMapping("{id}")
    Response getById(@PathVariable Long id) {
        return responseFactory.okOrHandleError(() -> categoryService.getById(id));
    }

    @PostMapping
    Response add(@RequestBody CategoryDTO body) {
        return responseFactory.createdOrHandleError(() -> categoryService.add(body));
    }

    @PutMapping("{id}")
    Response update(@PathVariable Long id, @RequestBody CategoryDTO body) {
        return responseFactory.okOrHandleError(() -> categoryService.update(id, body));
    }

    @DeleteMapping("{id}")
    Response deleteById(@PathVariable Long id) {
        return responseFactory.deletedOrHandleError(() -> categoryService.deleteById(id));
    }
}
