package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "opd_bills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpdBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment; // ✅ OPD Bills are linked to an appointment

    @OneToMany(mappedBy = "opdBill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillingItem> billingItems; // ✅ List of Billing Items (Consultation, Tests, etc.)

    @Embedded
    private InsuranceOption insuranceOption; // ✅ Embedded Insurance Information

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount; // ✅ Total Amount Before Discount

    @Column(name = "discount_amount")
    private Double discountAmount; // ✅ Discount Applied

    @Column(name = "final_amount", nullable = false)
    private Double finalAmount; // ✅ Amount After Discount & Insurance

    @Column(name = "is_insurance_applied", nullable = false)
    private Boolean isInsuranceApplied; // ✅ Flag to check if Insurance is Used

    @Embedded
    private Payment payment; // ✅ EMBEDDED Payment (Instead of Separate Entity)
}
