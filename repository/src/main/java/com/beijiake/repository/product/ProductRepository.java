package com.beijiake.repository.product;

import com.beijiake.data.domain.product.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
