package com.beijiake.web.service.category;

import com.beijiake.data.domain.attribute.Attribute;
import com.beijiake.repository.attribute.AttributeRepository;
import com.beijiake.data.domain.category.Category;
import com.beijiake.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AttributeRepository attributeRepository;

    /**
     * 获取最顶层分类
     * @return
     */
    public List<Category> getTopmostCategories() {

        return categoryRepository.findAllByParentIsNull();

    }

    /**
     * 获取指定分类的子类
     */



    /**
     * 获取指定分类的属性
     * @param category
     * @param bGetAncestors 是否获取父类的属性
     * @return
     */
    public List<Attribute> getCategoryAttributes(Category category, boolean bGetAncestors) {
        List<Attribute> attributes = attributeRepository.findAttributesByCategory(category);

        if (category.isInheritAttribute() && category.getParent() != null && bGetAncestors == true) {
            List<Attribute> parentAttributes = getCategoryAttributes(category.getParent(), bGetAncestors);

            for (Attribute attribute: parentAttributes) {
                if (!this.AttributesContains(attributes, attribute)) {
                    attributes.add(attribute);
                }
            }
        }

        return attributes;
    }


    /**
     * 获取指定分类的属性
     * @param categoryId
     * @param bGetAncestors
     * @return
     */
    public List<Attribute> getCategoryAttributes(Long categoryId, boolean bGetAncestors) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);

        return getCategoryAttributes(category, bGetAncestors);

    }

    private boolean AttributesContains(List<Attribute> attributes, Attribute findAttribute) {
        for (Attribute attribute: attributes) {
            if (attribute.getName().equals(findAttribute.getName()))
                return true;
        }

        return false;
    }

}
