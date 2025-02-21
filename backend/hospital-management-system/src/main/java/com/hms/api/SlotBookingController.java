package com.hms.api;

import com.hms.dto.SlotBookingRequest;
import com.hms.dto.SlotBookingResponse;
import com.hms.service.SlotBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
public class SlotBookingController {

    private final SlotBookingService slotBookingService;

    // ✅ Book a slot
    @PostMapping("/book")
    public ResponseEntity<SlotBookingResponse> bookSlot( @RequestBody SlotBookingRequest request) {
        return ResponseEntity.ok(slotBookingService.bookSlot(request));
    }

    // ✅ Get all bookings
    @GetMapping("/bookings")
    public ResponseEntity<List<SlotBookingResponse>> getAllBookings() {
        return ResponseEntity.ok(slotBookingService.getAllBookings());
    }

    // ✅ Get booking by ID
    @GetMapping("/bookings/{id}")
    public ResponseEntity<SlotBookingResponse> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(slotBookingService.getBookingById(id));
    }

    // ✅ Type-ahead search
    @GetMapping("/bookings/search")
    public ResponseEntity<List<SlotBookingResponse>> searchBookings(@RequestParam String keyword) {
        return ResponseEntity.ok(slotBookingService.searchBookings(keyword));
    }

    // ✅ Cancel booking
    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        slotBookingService.cancelBooking(id);
        return ResponseEntity.ok("Booking canceled successfully.");
    }
}
