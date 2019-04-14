package com.beijiake.web.domain.views.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = "children")
@Immutable
@Entity
@Table(name = "categories")
public class CategoryView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;


    @Column
    public String name;

    @ManyToOne
    @JoinTable(name ="category_relation",
            joinColumns = {@JoinColumn(name = "category_id")}, inverseJoinColumns = {@JoinColumn(name="parent_id")})
    @JsonBackReference
    CategoryView parent;


    @Column
    @ColumnDefault("true")
    boolean inheritAttribute;
}
