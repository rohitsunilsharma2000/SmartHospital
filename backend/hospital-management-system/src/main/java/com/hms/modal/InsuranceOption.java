package com.hms.modal;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String insuranceProviderName;
    private String policyId;
    private LocalDate expiryDate;
    private String insuranceName;
    private String policyHolder;
    private LocalDate paymentDate;
    private String companyCode;
    private String policyNumber;
    private String commentsForInsuranceOption;
    private Double insurancePaid;
    private Boolean includeConsumablesInPackage;

    private String tpaProvider;
    private String tpaId;
    private String tpaAuthId;
    private String employeeId;
    private String claimId;
}
