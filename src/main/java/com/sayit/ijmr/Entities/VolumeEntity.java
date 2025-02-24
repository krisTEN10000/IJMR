package com.sayit.ijmr.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "catogory")
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class VolumeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String volumeName;

    private String description;

    private String createdAt;

    @OneToMany(mappedBy = "volume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IssueEntity> issueEntities;
}
