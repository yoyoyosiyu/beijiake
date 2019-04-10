package com.beijiake.data.domain.attribute;

import com.beijiake.data.domain.category.ProductCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Set;


/**
 * 分类的属性表，当产品指定分类之后，分类的属性将会应用于该产品
 */
@Data
@EqualsAndHashCode(exclude = "productCategory")
@Entity
@Table(name = "attributes", uniqueConstraints = {@UniqueConstraint(columnNames = {"product_category_id", "name"})})
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JsonBackReference
    ProductCategory productCategory;

    @Column
    String name;


    @OneToMany(mappedBy = "attribute")
    @JsonManagedReference
    Set<AttributeOption> attributeOptions;

    /**
     * 是否必须
     */
    @Column
    @ColumnDefault("true")
    boolean required;

    @Column
    @ColumnDefault("false")
    boolean customizable;


}
