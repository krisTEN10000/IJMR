package com.sayit.ijmr.DTO;

import org.springframework.web.multipart.MultipartFile;

public record DocumentUploadDTO(MultipartFile multipartFile ,  String title ,  String Description ,  String volumeName ,  String issueName) {
}
