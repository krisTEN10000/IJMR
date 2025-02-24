package com.sayit.ijmr.Repository;

import com.sayit.ijmr.DTO.DocumentProjection;
import com.sayit.ijmr.Entities.DocumentsEntity;
import com.sayit.ijmr.Entities.VolumeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<DocumentsEntity, Long> {

    Optional<DocumentsEntity> findById(Long id);

    @Query("SELECT d.title AS name, d.description AS description FROM DocumentsEntity d " +
            "JOIN d.issue i " +
            "JOIN i.volume v " +
            "WHERE v.volumeName = :volumeName AND i.issueName = :issueName")
    List<DocumentProjection> findDocumentsByVolumeAndIssue(@Param("volumeName") String volumeName,
                                                           @Param("issueName") String issueName);
}
