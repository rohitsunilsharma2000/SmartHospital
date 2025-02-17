package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic patient information
    private String firstName;
    private String lastName;
    private Integer age;
    private String sex;  // e.g., "Male" or "Female"
    private Boolean registered;
    private String mobile;
    private LocalDate dateOfBirth;

    // Extended information
    private String motherTongue;
    private String hospId;
    private String patientTag;
    private String landline;
    private String fileNumber;
    private String govtId;
    private String maritalStatus;
    private String govtIdNumber;
    private String email;
    private String otherHospitalIds;

    // Referrer details
    private String referrerType;
    private String referrerName;
    private String referrerNumber;
    private String referrerEmail;

    // Additional details
    private String address;
    private String area;
    private String city;
    private String pinCode;
    private String state;
    private String country;
    private String nationality;

    // Other personal details
    private String patientPhoto; // e.g., URL or file path
    private String bloodGroup;
    private String fatherName;
    private String motherName;
    private String spouseName;
    private String alternateContact;
    private String education;
    private String occupation;
    private String religion;
    private String ivrLanguage;
    private String birthWeight;
    private String notes;

}
