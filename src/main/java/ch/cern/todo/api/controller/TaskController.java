package ch.cern.todo.api.controller;

import ch.cern.todo.api.dto.CategoryDTO;
import ch.cern.todo.api.dto.TaskDTO;
import ch.cern.todo.api.response.ContentResponse;
import ch.cern.todo.api.response.Response;
import ch.cern.todo.bll.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("task")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    Response getAll() {
        return ContentResponse.ok(taskService.getAll());
    }

    @GetMapping("{id}")
    Response getById(@PathVariable Long id) {
        return ContentResponse.ok(taskService.getById(id));
    }

    @PostMapping
    Response add(@RequestBody TaskDTO body) {
        return ContentResponse.created(taskService.add(body));
    }

    @PutMapping("{id}")
    Response update(@PathVariable Long id, @RequestBody TaskDTO body) {

        return ContentResponse.ok(taskService.update(id, body));
    }

    @DeleteMapping("{id}")
    Response deleteById(@PathVariable Long id) {
        taskService.deleteById(id);
        return Response.noContent();
    }
}
