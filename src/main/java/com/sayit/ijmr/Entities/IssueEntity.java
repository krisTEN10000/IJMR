package com.sayit.ijmr.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name ="sub_catogory")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IssueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String issueName;

    @ManyToOne
    @JoinColumn(nullable = false)
    private VolumeEntity volume;

    @OneToMany(mappedBy = "issue" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentsEntity> documentsEntityList;

}
