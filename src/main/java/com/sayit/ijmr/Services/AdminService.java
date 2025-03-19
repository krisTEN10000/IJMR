package com.sayit.ijmr.Services;

import com.sayit.ijmr.DTO.*;
import com.sayit.ijmr.Entities.DocumentsEntity;
import com.sayit.ijmr.Entities.IssueEntity;
import com.sayit.ijmr.Entities.VolumeEntity;
import com.sayit.ijmr.Exceptions.DocumentAlreadyExistException;
import com.sayit.ijmr.Exceptions.DocumentNotExistException;
import com.sayit.ijmr.Exceptions.IssueNotFoundException;
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
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final DocumentRepository documentRepository;

    private final VolumeRepository volumeRepository;

    private final IssueRepository issueRepository;

    public String uploadDocument( String title , String issueName ,String discription, MultipartFile multipartFile , String volumeName) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        byte[] document = inputStream.readAllBytes();
        Optional<VolumeEntity> volumeEntity =  volumeRepository.findByVolumeNameNative(volumeName);
        if (volumeEntity.isPresent()) {
            VolumeEntity volume = volumeEntity.get();
            IssueEntity issue = volume.getIssueEntities().stream().filter(i -> i.getIssueName().equals(issueName)).findFirst().orElse(null);
            if (issue != null) {
                Optional<DocumentsEntity> documentsEntity =  documentRepository.findDocumentByTitle(volumeName.concat(issueName).concat("_").concat(title));
                if (documentsEntity.isPresent()) {
                    throw new DocumentAlreadyExistException("Document already exists");
                }
                DocumentsEntity documentEntity =  DocumentsEntity
                        .builder()
                        .document(document)
                        .issue(issue)
                        .title(title)
                        .volume(volume)
                        .description(discription)
                        .build();
                documentRepository.save(documentEntity);
                return title;
            }
            throw new IssueNotFoundException("Issue " + issueName + " already exists");
        }else {
            throw new VolumeNotFoundException("Volume Not Found");
        }

    }

    @Transactional
    public String createIssue(CreateIssueDTO createIssueDTO) throws IOException {
        Optional<VolumeEntity> volumeEntity = volumeRepository.findByVolumeNameNative(createIssueDTO.getVolumeName());
        if (volumeEntity.isEmpty()) {
            throw new VolumeNotFoundException("Volume " + createIssueDTO.getVolumeName() + " not found");
        }else {
            VolumeEntity volume = volumeEntity.get();
            IssueEntity issue = volume.getIssueEntities().stream().filter(i -> i.getIssueName().equals(createIssueDTO.getIssueName())).findFirst().orElse(null);
            if(issue != null) {
                throw new IssueNotFoundException("Issue " + createIssueDTO.getIssueName() + " already exists");
            }else {
                IssueEntity issueToUpload = IssueEntity.builder()
                        .issueName(createIssueDTO.getIssueName())
                        .volume(volumeEntity.get())
                        .build();
                issueRepository.save(issueToUpload);
                return "Issue created successfully";
            }
        }

    }

    public List<DocumentsEntity> getAllDocuments(String issueName) {
     Optional<IssueEntity> issue = issueRepository.findByIssueName(issueName);
     if (issue.isEmpty()) {
         throw new RuntimeException("Issue " + issueName + " not found");
     }
     return issue.get().getDocumentsEntityList();
    }

    public String createVolume(String volumeName) throws IOException {
        if(volumeName == null || volumeName.isEmpty()) {
            throw new VolumeNotFoundException("Volume Name is null or empty");
        }
        Optional<VolumeEntity> volumeEntityOptional =  volumeRepository.findByVolumeNameNative(volumeName);
        if(volumeEntityOptional.isPresent()) {
            throw new VolumeNotFoundException("Volume " + volumeName + " already exists");
        }
        VolumeEntity volumeEntity  =  VolumeEntity
                .builder()
                .volumeName(volumeName)
                .build();
        volumeRepository.save(volumeEntity);
        return "Volume created successfully";
    }

    @Transactional
    public String editDocument( String title , String issueName ,String discription, MultipartFile multipartFile , String volumeName) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        byte[] document = inputStream.readAllBytes();
        Optional<VolumeEntity> volumeEntity =  volumeRepository.findByVolumeNameNative(volumeName);
        if (volumeEntity.isPresent()) {
            VolumeEntity volume = volumeEntity.get();
            IssueEntity issue = volume.getIssueEntities().stream().filter(i -> i.getIssueName().equals(issueName)).findFirst().orElse(null);
            if (issue != null) {
                Optional<DocumentsEntity> documentsEntity =  documentRepository.findDocumentByTitle(volumeName.concat(issueName).concat("_").concat(title));
                if (documentsEntity.isPresent()) {
                    DocumentsEntity documentEntity = documentsEntity.get();
                    documentEntity.setDocument(document);
                    documentEntity.setTitle(volumeName.concat(issueName).concat("_").concat(title));
                    documentEntity.setDescription(discription);
                    return title;
                }

            }
            throw new IssueNotFoundException("Issue " + issueName + " already exists");
        }else {
            throw new VolumeNotFoundException("Volume Not Found");
        }

    }

    public Map<String , List<String>> getAllDetails(){
        Map<String , List<String>> hashMap= new HashMap<>();
        hashMap.put("catagory" , volumeRepository.getAllVolumeName());
        hashMap.put("s_cat" , issueRepository.getAllIssueName());
        hashMap.put("documents" , documentRepository.getALLthatAreNotSpecial());
        hashMap.put("special" ,  documentRepository.getspecialDocuments());
        return  hashMap;
    }

    @Transactional
    public List<String> updateVolume(VolumeUpdateDTO volumeUpdateDTO) throws IOException {
        Optional<VolumeEntity> volumeEntity =  volumeRepository.findByVolumeNameNative(volumeUpdateDTO.volumeName());
        Optional<VolumeEntity> updatedVolumeEntity =  volumeRepository.findByVolumeNameNative(volumeUpdateDTO.updateVolumeName());
        if(updatedVolumeEntity.isPresent()) {
            throw new VolumeNotFoundException("Volume " + volumeUpdateDTO.volumeName() + " already exists");
        }
        if(volumeEntity.isPresent()) {
            VolumeEntity volume = volumeEntity.get();
            volume.setVolumeName(volumeUpdateDTO.updateVolumeName());
            return volumeRepository.getAllVolumeName();
        }
        throw new VolumeNotFoundException("Volume Not Found");
    }

    public List<List<String>> getAllTheIssues(){
        List<List<String>> listOfIssues = new ArrayList<>();
        for (Object[] obj : issueRepository.getAllIssueNames()) {
            listOfIssues.add(List.of(obj[0].toString(),obj[1].toString()));
        }
        return listOfIssues;
    }

    public List<String> getAllIssueName(String volumeName){
        return issueRepository.getAllIssueByVolumeName(volumeName);
    }

    public Map<String , Integer> getAllCount(){
        Map<String , Integer> hashMap= new HashMap<>();
        hashMap.put("normal" , documentRepository.getAllCountOfNoramlProduct());
        hashMap.put("special" , documentRepository.getAllCountOfSpecialProduct());
        return hashMap;
    }

    public List<FileSenderDTO> getAllDocumentDetails() {
        return documentRepository.getAllDocumentDetails();
    }
}
