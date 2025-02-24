package com.sayit.ijmr.Repository;

import com.sayit.ijmr.Entities.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<IssueEntity , Long> {
    public Optional<IssueEntity> findByIssueName(String issueName);
}
