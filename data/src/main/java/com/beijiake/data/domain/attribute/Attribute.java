package com.beijiake.data.domain.attribute;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.beijiake.data.domain.category.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Set;


/**
 * 分类的属性表，当产品指定分类之后，分类的属性将会应用于该产品
 */
@Data
@EqualsAndHashCode(exclude = "category")
@Entity
@Table(name = "attributes", uniqueConstraints = {@UniqueConstraint(columnNames = {"category_id", "name"})})
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JsonBackReference
    Category category;

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
