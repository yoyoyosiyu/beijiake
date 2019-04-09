package com.beijiake.data.domain.category;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "childCategories")
@Entity
@Table(name = "product_categories")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;


    @Column
    public String name;

    @ManyToOne
    @JoinTable(name ="product_category_relation",
    joinColumns = {@JoinColumn(name = "category_id")}, inverseJoinColumns = {@JoinColumn(name="parent_id")})
    @JsonBackReference
    ProductCategory parent;


    @OneToMany(mappedBy = "parent")
    @JsonManagedReference
    @OrderBy("id")
    public Set<ProductCategory> childCategories = Collections.emptySet();
}
