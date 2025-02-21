package com.hms.modal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @EmbeddedId
    private PatientId id; // Composite key

    private String firstName;
    private String lastName;
    private Integer age;
    private String sex;
    private Boolean registered;
    private String dateOfBirth;
    private String motherTongue;
    private String patientTag;
    private String landline;
    private String fileNumber;
    private String govtId;
    private String maritalStatus;
    private String govtIdNumber;
    private String email;
    private String otherHospitalIds;
    private String referrerType;
    private String referrerName;
    private String referrerNumber;
    private String referrerEmail;
    private String address;
    private String area;
    private String city;
    private String pinCode;
    private String state;
    private String country;
    private String nationality;
    private String patientPhoto;
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
