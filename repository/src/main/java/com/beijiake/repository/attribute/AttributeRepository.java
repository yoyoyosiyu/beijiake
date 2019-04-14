package com.beijiake.repository.attribute;

import com.beijiake.data.domain.attribute.Attribute;
import com.beijiake.data.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    List<Attribute> findAttributesByCategory(Category category);

}
