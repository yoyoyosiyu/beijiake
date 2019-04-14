package com.beijiake.web.rest.category;

import com.beijiake.data.domain.attribute.Attribute;
import com.beijiake.data.domain.category.Category;
import com.beijiake.repository.attribute.AttributeRepository;
import com.beijiake.repository.category.CategoryRepository;
import com.beijiake.web.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/${url.api.prefix:/api}/categories/{categoryId}/attributes")
public class CategoryAttributeController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AttributeRepository attributeRepository;

    @Autowired
    CategoryService productCategoryService;

    @PostMapping
    public void doPost(@PathVariable(name = "categoryId") Long categoryId, @RequestBody Attribute attribute) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);

        attribute.setCategory(category);

        attributeRepository.save(attribute);

    }

    @GetMapping
    public List<Attribute> doGet(@PathVariable Long categoryId) {

        return productCategoryService.getCategoryAttributes(categoryId, true);
    }


}
