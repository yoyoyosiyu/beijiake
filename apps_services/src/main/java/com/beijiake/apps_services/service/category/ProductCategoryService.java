package com.beijiake.apps_services.service.category;

import com.beijiake.data.domain.attribute.Attribute;
import com.beijiake.data.domain.category.Category;
import com.beijiake.repository.attribute.AttributeRepository;

import com.beijiake.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductCategoryService {

    @Autowired
    CategoryRepository CategoryRepository;

    @Autowired
    AttributeRepository attributeRepository;

    public List<Attribute> GetCategoryAttributes(Category Category, boolean bGetAncestors) {
        List<Attribute> attributes = attributeRepository.findAttributesByCategory(Category);

        if (Category.isInheritAttribute() && Category.getParent() != null && bGetAncestors == true) {
            List<Attribute> parentAttributes = GetCategoryAttributes(Category.getParent(), bGetAncestors);

            for (Attribute attribute: parentAttributes) {
                if (!this.AttributesContains(attributes, attribute)) {
                    attributes.add(attribute);
                }
            }
        }

        return attributes;
    }

    public List<Attribute> GetCategoryAttributes(Long categoryId, boolean bGetAncestors) {

        Category Category = CategoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);

        return GetCategoryAttributes(Category, bGetAncestors);

    }

    private boolean AttributesContains(List<Attribute> attributes, Attribute findAttribute) {
        for (Attribute attribute: attributes) {
            if (attribute.getName().equals(findAttribute.getName()))
                return true;
        }

        return false;
    }

}
