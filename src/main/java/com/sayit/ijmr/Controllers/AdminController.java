package com.sayit.ijmr.Controllers;

import com.sayit.ijmr.DTO.CreateIssueDTO;
import com.sayit.ijmr.DTO.UploderDTO;
import com.sayit.ijmr.DTO.VolumeCreatorDTO;
import com.sayit.ijmr.Services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/upload/document")
    public ResponseEntity<String> uploadDocument(
            @RequestParam String issueName,
            @RequestParam String title,
            @RequestParam String author,
            @RequestBody MultipartFile multipartFile
    ) throws IOException {
        return ResponseEntity.ok().body(
                adminService.uploadDocument(author , title , issueName , "" , multipartFile )
        );
    }


    private ResponseEntity<String> editDocument(){
        return ResponseEntity.ok().body("Edit Document");
    }

    @PutMapping("/create/sub-cat")
    private ResponseEntity<String> createIssue(@RequestBody CreateIssueDTO createIssueDTO) throws IOException {
        return ResponseEntity.ok().body(adminService.createIssue(createIssueDTO));
    }

    @PutMapping("/create/cat")
    private ResponseEntity<String> createVolume(@RequestBody VolumeCreatorDTO volumeCreatorDTO) throws IOException {
        return ResponseEntity.ok().body(adminService.createVolume(volumeCreatorDTO));
    }
}
