package com.hms.api;


import com.hms.dto.OpdBillRequest;
import com.hms.dto.OpdBillResponse;
import com.hms.service.OpdBillingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
    // ✅ Fetch OPD Bill by Appointment ID
    @GetMapping("/{appointmentId}")
    public ResponseEntity<OpdBillResponse> getOpdBillByAppointmentId(@PathVariable Long appointmentId) {
        log.info("🟢 Controller: Fetching OPD Bill for appointment ID: {}", appointmentId);
        OpdBillResponse response = opdBillingService.getOpdBillByAppointmentId(appointmentId);
        return ResponseEntity.ok(response);
    }

    // ✅ Update OPD Bill by Appointment ID
    @PutMapping("/{appointmentId}")
    public ResponseEntity<OpdBillResponse> updateOpdBill(@PathVariable Long appointmentId, @RequestBody OpdBillRequest request) {
        log.info("🟢 Controller: Updating OPD Bill for appointment ID: {}", appointmentId);
        OpdBillResponse response = opdBillingService.updateOpdBill(appointmentId, request);
        return ResponseEntity.ok(response);
    }
}
