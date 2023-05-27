package ch.cern.todo.api.controller;

import ch.cern.todo.bll.dto.TaskDTO;
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
        return responseFactory.okOrHandleError(() -> taskService.getById(id));
    }

    @PostMapping
    Response add(@RequestBody TaskDTO body) {
        return responseFactory.createdOrHandleError(() -> taskService.add(body));
    }

    @PutMapping("{id}")
    Response update(@PathVariable Long id, @RequestBody TaskDTO body) {
        return responseFactory.okOrHandleError(() -> taskService.update(id,body));
    }

    @DeleteMapping("{id}")
    Response deleteById(@PathVariable Long id) {
        return responseFactory.deletedOrHandleError(() -> taskService.deleteById(id));
    }


}
