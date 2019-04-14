package com.beijiake.data.domain.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "product_images")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler"})
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="product_fk")
    @JsonBackReference
    Product product;


    @Column
    String resourceUrl;

}
