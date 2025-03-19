package com.sayit.ijmr.Repository;

import com.sayit.ijmr.Entities.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<IssueEntity , Long> {


    public Optional<IssueEntity> findByIssueName(String issueName);

    @Query("select i.issueName from IssueEntity i where i.volume.volumeName = :volumeName")
    List<String> getAllIssueByVolumeName(String volumeName);

    @Query("select i.id from IssueEntity i where i.volume.volumeName = :volumeName")
    List<Long> getAllIssueIdByVolumeName(String volumeName);

    @Query("select i.id from IssueEntity i where i.issueName = :issueName AND i.volume.volumeName = :volumeName")
    Long getIsueIdByIssueNameAndVolumeName(String issueName,String volumeName);

    @Query("select i.issueName from IssueEntity i")
    List<String> getAllIssueName();

    @Query("select i.issueName , i.volume.volumeName  from IssueEntity i")
    List<Object[]> getAllIssueNames();

    @Query("select issue from IssueEntity issue where issue.issueName = :issueName and issue.volume.volumeName = :volumeName")
    Optional<IssueEntity> getIssueByIssueNameAndVolumeName(String issueName,String volumeName);
}
