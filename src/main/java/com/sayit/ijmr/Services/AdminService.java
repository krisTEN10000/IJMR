package com.sayit.ijmr.Services;

import com.sayit.ijmr.DTO.CreateIssueDTO;
import com.sayit.ijmr.DTO.UploderDTO;
import com.sayit.ijmr.DTO.VolumeCreatorDTO;
import com.sayit.ijmr.Entities.DocumentsEntity;
import com.sayit.ijmr.Entities.IssueEntity;
import com.sayit.ijmr.Entities.VolumeEntity;
import com.sayit.ijmr.Exceptions.VolumeNotFoundException;
import com.sayit.ijmr.Repository.DocumentRepository;
import com.sayit.ijmr.Repository.IssueRepository;
import com.sayit.ijmr.Repository.VolumeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final DocumentRepository documentRepository;

    private final VolumeRepository volumeRepository;

    private final IssueRepository issueRepository;

    public String uploadDocument(String author , String title , String issueName ,String discription, MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        byte[] document = inputStream.readAllBytes();
        Optional<IssueEntity> issue = issueRepository.findByIssueName(issueName);
        if (issue.isEmpty()) {
            throw new VolumeNotFoundException("Volume " + issueName + " not found");
        }
        DocumentsEntity documentsEntity =  DocumentsEntity
                .builder()
                .document(document)
                .issue(issue.get())
                .title(title)
                .description(discription)
                .build();

        documentRepository.save(documentsEntity);
        return "Documents uploaded successfully";
    }

    @Transactional
    public String createIssue(CreateIssueDTO createIssueDTO) throws IOException {
        Optional<VolumeEntity> volumeEntity = volumeRepository.findByVolumeNameNative(createIssueDTO.getVolumeName());
        if (volumeEntity.isEmpty()) {
            throw new VolumeNotFoundException("Volume " + createIssueDTO.getVolumeName() + " not found");
        }
        IssueEntity issue = IssueEntity.builder()
                                        .issueName(createIssueDTO.getIssueName())
                                        .volume(volumeEntity.get())
                                        .build();
        issueRepository.save(issue);
        return "Issue created successfully";
    }

    public List<DocumentsEntity> getAllDocuments(String issueName) {
     Optional<IssueEntity> issue = issueRepository.findByIssueName(issueName);
     if (issue.isEmpty()) {
         throw new RuntimeException("Issue " + issueName + " not found");
     }
     return issue.get().getDocumentsEntityList();
    }

    public String createVolume(VolumeCreatorDTO volumeCreatorDTO) throws IOException {
        VolumeEntity volumeEntity  =  VolumeEntity
                .builder()
                .volumeName(volumeCreatorDTO.getVolumeName())
                .createdAt(volumeCreatorDTO.getCreatedAt())
                .description(volumeCreatorDTO.getDescription())
                .build();
        volumeRepository.save(volumeEntity);
        return "Volume created successfully";
    }
}
