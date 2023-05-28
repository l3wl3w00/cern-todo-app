package ch.cern.todo.api.controller;

import ch.cern.todo.bll.dto.NoContentDTO;
import ch.cern.todo.bll.dto.ResponseTaskDTO;
import ch.cern.todo.bll.dto.TaskDTO;
import ch.cern.todo.api.response.ResponseFactory;
import ch.cern.todo.api.response.Response;
import ch.cern.todo.bll.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * Controller for managing the tasks. Supports basic CRUD functionality
 * Each endpoint returns with a response that is an instance of the Response<T> class.
 * If no errors happen, each endpoint returns with the OK (200) http response, except for when specified otherwise.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("task")
public class TaskController {
    private final TaskService taskService;
    private final ResponseFactory responseFactory;


    /**
     * Returns every task resource that is in the db.
     * @return The tasks
     */
    @GetMapping
    Response<Collection<ResponseTaskDTO>> getAll(HttpServletResponse response) {
        return responseFactory
                .ok(taskService::getAll)
                .setProperResponseValue(response);
    }

    /**
     * Returns the task resource that has the id provided as a path variable.
     * If the id provided is not a number, it will return bad request (400).
     * If there is no resource with the given id, it will return not found (404).
     * @param id The id provided in the path
     * @return The resource or a response that describes the problem with getting the resource
     */
    @GetMapping("{id}")
    Response<ResponseTaskDTO> getById(@PathVariable Long id, HttpServletResponse response) {
        return responseFactory
                .okOrHandleError(() -> taskService.getById(id))
                .setProperResponseValue(response);
    }

    /**
     * Creates a new task resource and saves it in the db.
     * If a provided data is invalid (e.g. too long name) it returns bad request (400).
     * If the categoryName field of the task is not the name of an existing category,
     * it will return not found (404).
     * If everything is alright, it returns the created resource (which will contain its ID),
     * and the created (201) http response.
     * @param body the parameters with which to create the resource
     * @return The created resource or a response that describes the problem with creating the resource
     */
    @PostMapping
    Response<ResponseTaskDTO> add(@RequestBody TaskDTO body, HttpServletResponse response) {
        return responseFactory
                .createdOrHandleError(() -> taskService.add(body))
                .setProperResponseValue(response);
    }

    /**
     * Updates an already existing resource.
     * If a provided data is invalid (e.g. too long name) it returns bad request (400).
     * If the categoryName field of the modified task is not the name of an existing category,
     * it will return not found (404).
     * If everything is alright, it returns the modified resource (which will contain its ID),
     * and the ok (200) http response.
     * @param id The id of the resource to modify
     * @param body The new modified state of the resource
     * @return The modified resource or a response that describes the problem with modifying the resource
     */
    @PutMapping("{id}")
    Response<ResponseTaskDTO> update(@PathVariable Long id, @RequestBody TaskDTO body, HttpServletResponse response) {
        return responseFactory
                .okOrHandleError(() -> taskService.update(id,body))
                .setProperResponseValue(response);
    }

    /**
     * Deletes an existing resource.
     * If no task exists with the provided id, it returns not found (404).
     * If everything is alright, it returns no extra content, just the basic response with no content (204) status code
     * @param id The id of the resource to delete
     * @return No content (204) http response or a response that describes the problem with deleting the resource
     */
    @DeleteMapping("{id}")
    Response<NoContentDTO> deleteById(@PathVariable Long id, HttpServletResponse response) {
        return responseFactory.deletedOrHandleError(() -> taskService.deleteById(id))
                .setProperResponseValue(response);
    }


}
