package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceOption {

    private String insuranceProviderName;
    private String policyId;         // Insurance policy ID
    private LocalDate expiryDate;
    private String insuranceName;
    private String policyHolder;
    private LocalDate paymentDate;
    private String companyCode;
    private String policyNumber;
    private String commentsForInsuranceOption;
    private Double insurancePaid;
    private Boolean includeConsumablesInPackage;

    private String tpaProvider;   // e.g., "MediBuddy"
    private String tpaId;
    private String tpaAuthId;
    private String employeeId;
    private String claimId;
}
