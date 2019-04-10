package com.beijiake.apps_services.service.category;

import com.beijiake.data.domain.attribute.Attribute;
import com.beijiake.data.domain.category.ProductCategory;
import com.beijiake.repository.attribute.AttributeRepository;
import com.beijiake.repository.category.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductCategoryService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    AttributeRepository attributeRepository;

    public List<Attribute> GetCategoryAttributes(ProductCategory productCategory, boolean bGetAncestors) {
        List<Attribute> attributes = attributeRepository.findAttributesByProductCategory(productCategory);

        if (productCategory.isInheritAttribute() && productCategory.getParent() != null && bGetAncestors == true) {
            List<Attribute> parentAttributes = GetCategoryAttributes(productCategory.getParent(), bGetAncestors);

            for (Attribute attribute: parentAttributes) {
                if (!this.AttributesContains(attributes, attribute)) {
                    attributes.add(attribute);
                }
            }
        }

        return attributes;
    }

    public List<Attribute> GetCategoryAttributes(Long categoryId, boolean bGetAncestors) {

        ProductCategory productCategory = productCategoryRepository.findById(categoryId).orElseThrow(EntityNotFoundException::new);

        return GetCategoryAttributes(productCategory, bGetAncestors);

    }

    private boolean AttributesContains(List<Attribute> attributes, Attribute findAttribute) {
        for (Attribute attribute: attributes) {
            if (attribute.getName().equals(findAttribute.getName()))
                return true;
        }

        return false;
    }

}
