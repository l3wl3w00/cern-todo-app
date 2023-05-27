package ch.cern.todo.api.controller;

import ch.cern.todo.api.dto.TaskDTO;
import ch.cern.todo.api.response.ResponseFactory;
import ch.cern.todo.api.response.Response;
import ch.cern.todo.bll.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("task")
public class TaskController {
    private final TaskService taskService;
    private final ResponseFactory responseFactory;

    @GetMapping
    Response getAll() {
        return responseFactory.ok(taskService::getAll);
    }

    @GetMapping("{id}")
    Response getById(@PathVariable Long id) {
        return responseFactory.okOrNotFound(() -> taskService.getById(id));
    }

    @PostMapping
    Response add(@RequestBody TaskDTO body) {
        return responseFactory.createdOrNotFound(() -> taskService.add(body));
    }

    @PutMapping("{id}")
    Response update(@PathVariable Long id, @RequestBody TaskDTO body) {
        return responseFactory.okOrNotFound(() -> taskService.update(id,body));
    }

    @DeleteMapping("{id}")
    Response deleteById(@PathVariable Long id) {
        return responseFactory.deletedOrNotFound(() -> taskService.deleteById(id));
    }


}
