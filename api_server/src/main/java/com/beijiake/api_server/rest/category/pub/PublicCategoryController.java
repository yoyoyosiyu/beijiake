package com.beijiake.api_server.rest.category.pub;

import com.beijiake.data.domain.category.Category;
import com.beijiake.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("${url-patterns.public:/pub}/categories")
public class PublicCategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/{id}")
    public Category doGet(@PathVariable("id")Long id) {
        return categoryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(String.format("category with id %d does not exist", id)));
    }
}
