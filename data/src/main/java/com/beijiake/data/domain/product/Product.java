package com.beijiake.data.domain.product;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "images")
@Entity
@Table(name = "products")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler"})
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<ProductImage> images = Collections.emptySet();


    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<ProductAttribute> attributes = Collections.emptySet();

}
