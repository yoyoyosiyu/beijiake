package com.beijiake.data.domain.resouce;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "usages")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column
    String filename;

    @Column
    String url;

    @Column
    Long size;


    @Column
    String type;

    @OneToMany(mappedBy = "file", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<FileUsage> usages = Collections.emptySet();


    @CreatedDate
    @Column
    Date createAt;

    @LastModifiedDate
    @Column
    Date updateAt;

}
