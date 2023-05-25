package ch.cern.todo.api.controller;

import ch.cern.todo.api.dto.CategoryDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("category")
public class CategoryController {


    @GetMapping
    Collection<CategoryDTO> getAll(){
        var response = new ArrayList<CategoryDTO>();
        response.add(new CategoryDTO("category1","description1"));
        return response;
    }
}
