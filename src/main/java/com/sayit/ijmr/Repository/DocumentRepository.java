package com.sayit.ijmr.Repository;

import com.sayit.ijmr.DTO.DocumentProjection;
import com.sayit.ijmr.DTO.FileSenderDTO;
import com.sayit.ijmr.Entities.DocumentsEntity;
import com.sayit.ijmr.Entities.IssueEntity;
import com.sayit.ijmr.Entities.VolumeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

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

    Optional<DocumentsEntity> findDocumentByTitle(String title);

    @Query("select doc.title from DocumentsEntity doc where doc.issue.id = :issueId")
    List<String> findAllDocumentsByIssueId(Long issueId);

    @Query("select doc.title from DocumentsEntity doc")
    List<String> getAllDocumentTitle();

    @Query("select doc.title from DocumentsEntity doc where doc.volume.volumeName in ('special')")
    List<String> getspecialDocuments();

    @Query("select doc.title from DocumentsEntity doc where doc.volume.volumeName NOT in ('special') ")
    List<String> getALLthatAreNotSpecial();

    @Query("select doc from DocumentsEntity doc where doc.volume=:volume and doc.issue = :issue")
    Optional<DocumentsEntity> findDocumentByVolumeAndIssue(VolumeEntity volume , IssueEntity issue);

    @Query("select issue.issueName from IssueEntity issue where issue.volume.volumeName = :volume")
    List<String> getAllIsuueNames(@Param("volume") String volumeName);
//    Optional<DocumentsEntity> findDocumentsByTitleAndVolumeAndIssue(@Param("title") String title ,@Param("volumeName") String volumeName,
//                                                            @Param("issueName") String issueName);

    @Query("select count(doc) from DocumentsEntity doc where doc.volume.volumeName not in ('SPECIAL') ")
    Integer getAllCountOfNoramlProduct();

    @Query("select  count(doc) from DocumentsEntity doc where  doc.volume.volumeName in ('SPECIAL')")
    Integer getAllCountOfSpecialProduct();

    @Query("select new com.sayit.ijmr.DTO.FileSenderDTO(doc.title ,  doc.volume.volumeName ,  doc.issue.issueName) from DocumentsEntity  doc")
    List<FileSenderDTO> getAllDocumentDetails();
}
