package com.beijiake.data.domain.attribute;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="attribute_options", uniqueConstraints = {@UniqueConstraint(columnNames = {"attribute_id", "optionValue"})})
public class AttributeOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JsonBackReference
    Attribute attribute;

    @Column
    String optionValue;

}
