package com.sayit.ijmr.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileSenderDTO {
    private String fileName;
    private String volumeName;
    private String issueName;
}
