package com.beijiake.repository.category;

import com.beijiake.data.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public List<Category> findAllByParentIsNull();
}
