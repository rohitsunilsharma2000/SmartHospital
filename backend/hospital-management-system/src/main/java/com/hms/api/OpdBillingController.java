package com.hms.api;


import com.hms.dto.OpdBillRequest;
import com.hms.dto.OpdBillResponse;
import com.hms.service.OpdBillingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/opd-billing")
public class OpdBillingController {

    private final OpdBillingService opdBillingService;

    public OpdBillingController(OpdBillingService opdBillingService) {
        this.opdBillingService = opdBillingService;
    }

    @PostMapping("/create")
    public ResponseEntity<OpdBillResponse> createOpdBill(@RequestBody OpdBillRequest request) {
        return ResponseEntity.ok(opdBillingService.createOpdBill(request));
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<OpdBillResponse> getOpdBill(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(opdBillingService.getOpdBill(appointmentId));
    }
}
