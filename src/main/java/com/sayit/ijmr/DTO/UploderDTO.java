package com.sayit.ijmr.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploderDTO {
    private String issueName;

    private String title;

    private String author;

    private Long issue;

}
