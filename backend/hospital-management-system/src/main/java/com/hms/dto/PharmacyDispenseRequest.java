package com.hms.dto;

import com.hms.modal.CustomMedicine;
import com.hms.modal.Discount;
import com.hms.modal.InsuranceOption;
import lombok.Data;

import java.util.List;

@Data
public class PharmacyDispenseRequest {
    private Long prescriptionId;  // ✅ Prescription-based dispense (optional)
    private List<Long> medicineIds;  // ✅ Manual medicine selection (optional)
    private List<CustomMedicine> customMedicines;  // ✅ Custom medicines added manually
    private Long bookingId;  // ✅ Required to link bill to the patient
    private Double amountPaid;
    private String paymentMethod;
    private Discount discount;
    private InsuranceOption insurance;
//    private BillCategory billCategory; // ✅ New field for bill category (OPD / PHARMACY)

}
