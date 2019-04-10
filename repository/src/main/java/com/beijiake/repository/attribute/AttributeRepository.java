package com.beijiake.repository.attribute;


import com.beijiake.data.domain.attribute.Attribute;
import com.beijiake.data.domain.category.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    List<Attribute> findAttributesByProductCategory(ProductCategory productCategory);

}
