package com.sayit.ijmr.Services;

import com.sayit.ijmr.Entities.DocumentsEntity;
import com.sayit.ijmr.Entities.IssueEntity;
import com.sayit.ijmr.Entities.VolumeEntity;
import com.sayit.ijmr.Exceptions.IssueNotFoundException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final DocumentRepository documentRepository;
    private final VolumeRepository volumeRepository;
    private final IssueRepository issueRepository;

    public InputStreamSource getDocumentAsStream(String volumeName , String issueName , String title) {
        Optional<DocumentsEntity> findById = documentRepository.findDocumentByTitle(volumeName.concat(issueName).concat("_").concat(title));
        if (findById.isPresent()) {
            DocumentsEntity documentsEntity = findById.get();
            byte[] documentArray =  documentsEntity.getDocument();
            InputStream inputStream = new ByteArrayInputStream(documentArray);
            return new InputStreamResource(inputStream);
        }
        throw new RuntimeException("Document not found");
    }
    public InputStreamSource getDocumentAsStreamFromIAV(String volumeName , String issueName , String title) {
        Optional <VolumeEntity> volumeEntity =  volumeRepository.findByVolumeNameNative(volumeName);
        Optional<IssueEntity> issueEntity =  issueRepository.findByIssueName(issueName);
        if(volumeEntity.isPresent() && issueEntity.isPresent()) {
            Optional<DocumentsEntity>  documentsEntity= documentRepository.findDocumentByVolumeAndIssue(volumeEntity.get() , issueEntity.get());
            if (documentsEntity.isPresent()) {
                DocumentsEntity document = documentsEntity.get();
                byte[] documentArray =  document.getDocument();
                InputStream inputStream = new ByteArrayInputStream(documentArray);
                return new InputStreamResource(inputStream);
            }

        }
        throw new RuntimeException("Document not found");
    }

    public List<String> getVolumeAndItsIssues() {
        List<String> volumeEntities = volumeRepository.findAllBWithRegex();
        return volumeEntities;
    }

    public List<DocumentsEntity> getAllDocumentsFromIssue(String volumeName , String issueName) {
        Optional<VolumeEntity> volumeEntity = volumeRepository.findByVolumeNameNative(volumeName);
        if(volumeEntity.isEmpty()){
            throw new VolumeNotFoundException("Volume " + volumeName + " not found");
        }else {
            Optional<IssueEntity> issueEntity =  issueRepository.findByIssueName(issueName);
            if(issueEntity.isEmpty()){
                throw new IssueNotFoundException("Issue " + issueName + " not found");
            }else {
                IssueEntity issueEntity1 = issueEntity.get();
                List<DocumentsEntity> documentsEntityList = issueEntity1.getDocumentsEntityList();
                return documentsEntityList;
            }

        }

    }
    public List<String> getAllIssueByVolumeName(String volumeName) {
        return issueRepository.getAllIssueByVolumeName(volumeName);
    }

    public List<String> getAllDocumentsByIssueName(String issueName , String volumeName) {
        long issueId =  issueRepository.getIsueIdByIssueNameAndVolumeName(issueName,volumeName);
        return documentRepository.findAllDocumentsByIssueId(issueId);
    }
}
