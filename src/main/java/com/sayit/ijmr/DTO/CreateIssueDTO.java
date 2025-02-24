package com.sayit.ijmr.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIssueDTO {
    private String issueName;

    private String volumeName;

}
