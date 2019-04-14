package com.beijiake.apps_services.web.category.pub;

import com.beijiake.data.domain.category.Category;
import com.beijiake.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("${url-patterns.public:/pub}/categories")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @CrossOrigin
    @GetMapping("/{id}")
    public Category doGet(@PathVariable("id")Long id) {
        return categoryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.format("category with id %d does not exist", id)));
    }
}
