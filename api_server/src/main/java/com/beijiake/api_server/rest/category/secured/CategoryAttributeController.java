package com.beijiake.api_server.rest.category.secured;

import com.beijiake.api_server.service.category.CategoryService;
import com.beijiake.data.domain.attribute.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${url-patterns.secured:/api/secured}/categories/{categoryId}/attributes")
public class CategoryAttributeController {


    @Autowired
    CategoryService categoryService;

    @PostMapping
    public Attribute doPost(@PathVariable Long categoryId, @RequestBody Attribute attribute) {

        return categoryService.addAttribute(categoryId, attribute);

    }

    @GetMapping
    public List<Attribute> doGet(@PathVariable Long categoryId) {

        return categoryService.getCategoryAttributes(categoryId, true);
    }


}
