package com.beijiake.repository.product;

import com.beijiake.data.domain.product.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
}
