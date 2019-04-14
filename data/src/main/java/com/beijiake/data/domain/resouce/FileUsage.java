package com.beijiake.data.domain.resouce;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class FileUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    File file;

    @Column
    String ownerType;


    @Column
    String ownerId;

}
