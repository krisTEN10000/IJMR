package com.sayit.ijmr.Controllers;

import com.sayit.ijmr.DTO.UploderDTO;
import com.sayit.ijmr.Entities.VolumeEntity;
import com.sayit.ijmr.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get/document/{title}")
    public ResponseEntity<InputStreamSource> getDocument(@PathVariable String title , @RequestParam String issueName , @RequestParam String volumeName) {
        return ResponseEntity.ok().body(userService.getDocumentAsStream(volumeName , issueName , title));
    }



    @GetMapping
    public ResponseEntity<List<String>> getAllResults(){
        return ResponseEntity.ok().body(userService.getVolumeAndItsIssues());
    }

    @GetMapping("/get/issues")
    public ResponseEntity<List<String>> getAllIssues(@RequestParam String volumeName) {
        return ResponseEntity.ok().body(userService.getAllIssueByVolumeName(volumeName));
    }

    @GetMapping("/get/document/list")
    public ResponseEntity<List<String>> getAllDocumentsFromIssue(@RequestParam String issueName , @RequestParam String volumeName) {
        return ResponseEntity.ok().body(userService.getAllDocumentsByIssueName(issueName , volumeName));
    }
}
