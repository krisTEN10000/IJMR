package com.sayit.ijmr.Services;

import com.sayit.ijmr.Entities.DocumentsEntity;
import com.sayit.ijmr.Entities.IssueEntity;
import com.sayit.ijmr.Entities.VolumeEntity;
import com.sayit.ijmr.Exceptions.VolumeNotFoundException;
import com.sayit.ijmr.Repository.DocumentRepository;
import com.sayit.ijmr.Repository.IssueRepository;
import com.sayit.ijmr.Repository.VolumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final DocumentRepository documentRepository;

    private final VolumeRepository volumeRepository;

    private final IssueRepository issueRepository;
    public InputStreamSource getDocumentAsStream(Long id) {
        Optional<DocumentsEntity> findById = documentRepository.findById(id);
        if (findById.isPresent()) {
            DocumentsEntity documentsEntity = findById.get();
            byte[] documentArray =  documentsEntity.getDocument();
            InputStream inputStream = new ByteArrayInputStream(documentArray);
            return new InputStreamResource(inputStream);
        }
        throw new RuntimeException("Document not found");
    }

    public List<DocumentsEntity> getAllDocumentsFromIssue(String volumeName , String issueName) {
        Optional<VolumeEntity> volumeEntity = volumeRepository.findByVolumeNameNative(volumeName);
        if(volumeEntity.isEmpty()){
            throw new VolumeNotFoundException("Volume " + volumeName + " not found");
        }
        IssueEntity issue = volumeEntity.get().getIssueEntities().stream().filter(i -> i.getIssueName().equals(issueName)).findFirst().get();
        List<DocumentsEntity> documentsEntityList = issue.getDocumentsEntityList();
        return documentsEntityList;


    }
}
