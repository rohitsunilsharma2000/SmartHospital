package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "insurance_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "opd_bill_id", nullable = false)
    private OpdBill opdBill;

    private String insuranceProvider;
    private String policyId;
    private LocalDate expiryDate;
    private String policyHolder;
    private String policyNumber;
    private Double insurancePaidAmount;
}
