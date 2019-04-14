package com.beijiake.data.domain.category;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "children")
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;


    @Column
    public String name;

    @ManyToOne
    @JoinTable(name ="category_relation",
    joinColumns = {@JoinColumn(name = "category_id")}, inverseJoinColumns = {@JoinColumn(name="parent_id")})
    @JsonBackReference
    Category parent;


    @OneToMany(mappedBy = "parent")
    @JsonManagedReference
    @OrderBy("id")
    public Set<Category> children = Collections.emptySet();

    @Column
    @ColumnDefault("true")
    boolean inheritAttribute;
}
