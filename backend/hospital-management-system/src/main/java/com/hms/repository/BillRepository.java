package com.hms.repository;

import com.hms.modal.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    // Search by multiple params (patient name, doctor name, slot number, transaction ID, payment status)
    List<Bill> findByBookingPatientFirstNameContainingIgnoreCaseOrBookingDoctorNameContainingIgnoreCaseOrBookingSlotSlotNumberContainingIgnoreCaseOrTransactionIdContainingIgnoreCaseOrPaymentDone(
            String patientName, String doctorName, String slotNumber, String transactionId, Boolean paymentDone);

    boolean existsByBookingId ( Long bookingId );

    boolean existsByBookingIdAndIdNot ( Long bookingId , Long billId );
}
