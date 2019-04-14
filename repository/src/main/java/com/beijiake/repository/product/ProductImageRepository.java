package com.beijiake.repository.product;

import com.beijiake.data.domain.product.Product;
import com.beijiake.data.domain.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findAllByProduct(Product product);

    Optional<ProductImage> findFirstByProductAndResourceUrl(Product product, String resourceUrl);
}
