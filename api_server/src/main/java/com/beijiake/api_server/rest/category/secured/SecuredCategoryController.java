package com.beijiake.api_server.rest.category.secured;


import com.beijiake.api_server.exception.ReferenceViolationException;
import com.beijiake.api_server.service.category.CategoryService;
import com.beijiake.data.domain.category.Category;
import com.beijiake.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@RestController
@RequestMapping("${url-patterns.secured:/api/secured}/categories")
@PreAuthorize("#oauth2.hasScope('management')")
public class SecuredCategoryController {

    @Autowired
    CategoryRepository productCategoryRepository;

    @Autowired
    CategoryService categoryService;


    /**
     *
     * @param categories
     * @return
     *
     * 在指定的分类下面添加分类，支持添加多个分类
     */
    @PostMapping("/{id}")
    public  List<Category> addCategory(@PathVariable(name="id")Long id, @RequestBody List<Category> categories) {

        return categoryService.addCategories(id, categories);

    }

    @PostMapping("/import")
    public void doImport(@RequestBody Map<String, Object> map) {

        return;

    }


    @DeleteMapping("/{id}")
    public void doDeleteCategory(@PathVariable Long id, @RequestParam(name="recurse", required = false, defaultValue = "false") boolean recurse) {

        categoryService.deleteCategory(id, recurse);

    }

}
