package com.hms.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientResponse {
    private String mobile;
    private String hospId;
    private String firstName;
    private String lastName;
    private String email;
}
