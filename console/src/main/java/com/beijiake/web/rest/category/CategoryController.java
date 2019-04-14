package com.beijiake.web.rest.category;

import com.beijiake.web.domain.views.category.CategoryView;
import com.beijiake.web.rest.ReferenceViolationException;
import com.beijiake.data.domain.category.Category;
import com.beijiake.repository.category.CategoryRepository;
import com.beijiake.web.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@RestController
@RequestMapping("/${url.api.prefix:/api}/categories")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    com.beijiake.web.views.CategoryViewRepository categoryViewRepository;

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<Category> doGetTopmostCategories() {
        return categoryService.getTopmostCategories();
    }

    //@CrossOrigin
    @GetMapping("/{id}")
    public Category doGet(@PathVariable("id")Long id) {
        return categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    /**
     *
     * @param category
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
    public Category doPost(@RequestBody Category category) {

        return categoryRepository.save(category);
    }

    /**
     *
     * @param categories
     * @return
     *
     * 在指定的分类下面添加分类，支持添加多个分类
     */
    @PostMapping("/{id}")
    public  Set<Category> addCategory(@PathVariable(name="id")Long id, @RequestBody Set<Category> categories) {

        if (categories.isEmpty()) return Collections.emptySet();

        Category productCategory = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        Set<Category> results = new HashSet<>();

        categories.forEach((category) ->{
            category.setParent(productCategory);
            category.setChildren(Collections.emptySet());
            results.add(categoryRepository.save(category));
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

        Category productCategory =  categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        Set<Category> childCategories = productCategory.getChildren();

        if (childCategories == null || childCategories.isEmpty())
        {
            categoryRepository.delete(productCategory);
            return;
        }

        if (recurse == false) {
            throw new ReferenceViolationException("The category hove more than one child, so remove all childes before delete current category");
        }

        childCategories.forEach((category) -> {
            deleteCategory(category.getId(), recurse);
        });

        categoryRepository.delete(productCategory);

    }

    @RequestMapping("/{id}/children")
    public List<CategoryView> doGetChildren(@PathVariable Long id){
        CategoryView categoryView = categoryViewRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
        List<CategoryView> c = categoryViewRepository.findAllByParent(categoryView);
        return c;
    }

}
