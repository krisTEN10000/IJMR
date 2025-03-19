package com.sayit.ijmr.Controllers;

import com.sayit.ijmr.DTO.*;
import com.sayit.ijmr.Services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/upload/document")
    public ResponseEntity<String> uploadDocument(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("issueName") String issueName,
            @RequestParam("volumeName") String volumeName,
            @RequestParam("multipartFile") MultipartFile multipartFile
            ) throws IOException {
        return ResponseEntity.ok().body(
                adminService.uploadDocument( title
                        , issueName, description , multipartFile ,volumeName )
        );
    }

    @PostMapping("/edit/document")
    public ResponseEntity<String> editDocument(
            @RequestParam String issueName,
            @RequestParam String title,
            @RequestParam String discription,
            @RequestBody MultipartFile multipartFile,
            @RequestParam String volumeName
    ) throws IOException {
        return ResponseEntity.ok().body(adminService.editDocument(title ,issueName ,discription , multipartFile , volumeName));
    }

    @PutMapping("/create/sub-catagory")
    public ResponseEntity<String> createIssue(@RequestBody CreateIssueDTO createIssueDTO) throws IOException {
        return ResponseEntity.ok().body(adminService.createIssue(createIssueDTO));
    }

    @PutMapping("/create/catatogory/{volumeName}")
    public ResponseEntity<String> createVolume(@PathVariable String volumeName) throws IOException {
        return ResponseEntity.ok().body(adminService.createVolume(volumeName));
    }


    @DeleteMapping("/delete/document")
    public ResponseEntity<String> deleteDocument(@RequestParam String issueName , String volumeName) throws IOException {
        return ResponseEntity.ok().body("Delete Document");
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(){
        return ResponseEntity.ok().body(true);
    }


    @GetMapping("/get/all/details")
    public ResponseEntity<Map<String , List<String>>> getDetails() {
        return ResponseEntity.ok().body(adminService.getAllDetails());
    }

    @PutMapping("/update/volumename/")
    public ResponseEntity<List<String>> updateVolume(@RequestBody VolumeUpdateDTO volumeUpdateDTO) throws IOException {
        return ResponseEntity.ok().body(adminService.updateVolume(volumeUpdateDTO));
    }

    @GetMapping("/get/all/Issues")
    public ResponseEntity<List<List<String>>> getAllIssues() {
        return ResponseEntity.ok().body(adminService.getAllTheIssues());
    }


    @GetMapping("/get/all/issue/{volumeName}")
    public ResponseEntity<List<String>> getAllIssues(@PathVariable String volumeName) throws IOException {
        return ResponseEntity.ok().body(adminService.getAllIssueName(volumeName));
    }

    @PutMapping("/update/Issue")
    public ResponseEntity<String> updateIssue(){
        return ResponseEntity.ok().body("Update Issue");
    }


    @GetMapping("/get/all/count")
    public ResponseEntity<Map<String , Integer>> getAllCount() {
        return ResponseEntity.ok().body(adminService.getAllCount());
    }

    @GetMapping("/get/documents/detail")
    public ResponseEntity<List<FileSenderDTO>> getAllDocuments() {
        return ResponseEntity.ok().body(adminService.getAllDocumentDetails());
    }
}
