package com.beijiake.apps_services.web.category.pub;

import com.beijiake.data.domain.category.ProductCategory;
import com.beijiake.repository.category.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("${url-patterns.public:/pub}/categories")
public class CategoryController {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @CrossOrigin
    @GetMapping("/{id}")
    public ProductCategory doGet(@PathVariable("id")Long id) {
        return productCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
