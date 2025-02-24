package com.sayit.ijmr.Controllers;

import com.sayit.ijmr.DTO.UploderDTO;
import com.sayit.ijmr.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get/document/{documentName}")
    public String getDocument(@PathVariable String documentName) {
        return null;
    }

    @GetMapping("/get/docs/{issue}")
    public ResponseEntity<String> getAllDocumentsFromIssues(@PathVariable String issue) {
        return null;
    }


    @GetMapping("/get/all/docs/volume")
    public ResponseEntity<String> getAllDocumentsFromVolume(@RequestParam String volumeName , @RequestParam String issueName) {
    return null;
    }
}
