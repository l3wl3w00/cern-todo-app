package ch.cern.todo.api.controller;

import ch.cern.todo.api.dto.CategoryDTO;
import ch.cern.todo.api.dto.TaskDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("task")
public class TaskController {

    @GetMapping
    Collection<TaskDTO> getAll(){
        var response = new ArrayList<TaskDTO>();

        var taskDto = new TaskDTO();
        taskDto.setName("task1");
        taskDto.setDescription("description2");
        taskDto.setDeadline(LocalDate.now());
        taskDto.setCategory(new CategoryDTO("category1", "description1"));

        response.add(taskDto);
        return response;
    }
}
