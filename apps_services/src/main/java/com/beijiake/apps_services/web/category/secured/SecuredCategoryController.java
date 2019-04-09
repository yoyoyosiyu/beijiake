package com.beijiake.apps_services.web.category.secured;


import com.beijiake.apps_services.exception.ReferenceViolationException;
import com.beijiake.data.domain.category.ProductCategory;
import com.beijiake.repository.category.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("${url-patterns.secured:/secured}/categories")
@PreAuthorize("#oauth2.hasScope('management')")
public class SecuredCategoryController {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @GetMapping
    public String doGet() {
        return "Secured Categories Management";
    }


    /**
     *
     * @param productCategory
     * @return
     *
     * 添加分类，如果添加顶层分类
     *
     * {
     *     "name": "category name"
     * }
     *
     * 如果添加子分类
     *
     * {
     *     "parent": {
     *         "id": 1
     *     },
     *     "name": "child category"
     * }
     */
    @PostMapping
    public ProductCategory doPost(@RequestBody ProductCategory productCategory) {

        return productCategoryRepository.save(productCategory);
    }

    /**
     *
     * @param categories
     * @return
     *
     * 在指定的分类下面添加分类，支持添加多个分类
     */
    @PostMapping("/{id}")
    public  Set<ProductCategory> addCategory(@PathVariable(name="id")Long id, @RequestBody Set<ProductCategory> categories) {

        if (categories.isEmpty()) return Collections.emptySet();

        ProductCategory productCategory = productCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        Set<ProductCategory> results = new HashSet<>();

        categories.forEach((category) ->{
            category.setParent(productCategory);
            category.setChildCategories(Collections.emptySet());
            results.add(productCategoryRepository.save(category));
        });

        return results;

    }

    @PostMapping("/import")
    public void doImport(@RequestBody Map<String, Object> map) {

        return;

    }


    @DeleteMapping("/{id}")
    public void doDeleteCategory(@PathVariable Long id, @RequestParam(name="recurse", required = false, defaultValue = "false") boolean recurse) {

        deleteCategory(id, recurse);

    }

    private void deleteCategory(Long id, boolean recurse) {

        ProductCategory productCategory =  productCategoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        Set<ProductCategory> childCategories = productCategory.getChildCategories();

        if (childCategories == null || childCategories.isEmpty())
        {
            productCategoryRepository.delete(productCategory);
            return;
        }

        if (recurse == false) {
            throw new ReferenceViolationException("The category hove more than one child, so remove all childes before delete current category");
        }

        childCategories.forEach((category) -> {
            deleteCategory(category.getId(), recurse);
        });

        productCategoryRepository.delete(productCategory);

    }

}
