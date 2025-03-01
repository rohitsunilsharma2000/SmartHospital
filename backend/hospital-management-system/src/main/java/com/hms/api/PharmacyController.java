package com.hms.api;

import com.hms.dto.PharmacyBillResponse;
import com.hms.dto.PharmacyDispenseRequest;
import com.hms.dto.PharmacyPaymentRequest;
import com.hms.dto.PharmacyPaymentResponse;
import com.hms.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pharmacy")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;

    // ✅ Generate Bill Before Payment (Medicine Selection)
    @PostMapping("/dispense")
    public ResponseEntity<PharmacyBillResponse> generateBillBeforePayment(@RequestBody PharmacyDispenseRequest request) {
        return ResponseEntity.ok(pharmacyService.generateBillBeforePayment(request));
    }

    // ✅ Make Payment for a Bill
    @PostMapping("/payment")
    public ResponseEntity<PharmacyPaymentResponse> makePayment( @RequestBody PharmacyPaymentRequest request) {
        return ResponseEntity.ok(pharmacyService.makePayment(request));
    }
}
