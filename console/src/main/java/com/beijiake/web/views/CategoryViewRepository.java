package com.beijiake.web.views;


import com.beijiake.web.domain.views.category.CategoryView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryViewRepository extends JpaRepository<CategoryView, Long> {


    List<CategoryView> findAllByParent(CategoryView categoryView);

}
