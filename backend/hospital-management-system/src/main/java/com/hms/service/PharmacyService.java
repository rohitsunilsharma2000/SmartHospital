package com.hms.service;

import com.hms.dto.PharmacyBillResponse;
import com.hms.dto.PharmacyDispenseRequest;
import com.hms.dto.PharmacyPaymentRequest;
import com.hms.dto.PharmacyPaymentResponse;
//import com.hms.enums.BillCategory;
import com.hms.exception.*;
import com.hms.modal.*;
import com.hms.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyService {

    private final MedicineRepository medicineRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final SlotBookingRepository slotBookingRepository;
    private final BillRepository billRepository;
    private final DiscountRepository discountRepository;
    private final InsuranceRepository insuranceRepository;

    @Transactional
    public PharmacyBillResponse generateBillBeforePayment ( PharmacyDispenseRequest request ) {
        log.info("Generating bill for Booking ID: {}" , request.getBookingId());

        // ✅ Validate Booking
        SlotBooking booking = slotBookingRepository.findById(request.getBookingId())
                                                   .orElseThrow(() -> new BookingNotFoundException(
                                                           "Booking not found for ID: " + request.getBookingId()));

        // ✅ Check if a pharmacy bill already exists
//        if (billRepository.existsByBookingIdAndBillCategory(request.getBookingId() , BillCategory.PHARMACY)) {
//            throw new DuplicateBillException("A pharmacy bill already exists for this booking.");
//        }

        List<Medicine> medicines = new ArrayList<>();

        // ✅ Fetch Medicines based on Prescription ID or Manual Selection
        if (request.getPrescriptionId() != null) {
            Prescription prescription = prescriptionRepository.findById(request.getPrescriptionId())
                                                              .orElseThrow(() -> new PrescriptionNotFoundException(
                                                                      "Prescription not found with ID: " + request.getPrescriptionId()));

            medicines.addAll(prescription.getMedicines());
        }

        if (request.getMedicineIds() != null && !request.getMedicineIds().isEmpty()) {
            medicines.addAll(medicineRepository.findAllById(request.getMedicineIds()));
        }

        // ✅ Calculate Total Cost
        double totalAmount = medicines.stream().mapToDouble(Medicine::getPrice).sum();

        // ✅ Create Bill
        Bill bill = new Bill();
        bill.setBooking(booking);
//        bill.setBillCategory(request.getBillCategory());  // ✅ Save Bill Category
        bill.setTotalAmount(totalAmount);
        bill.setAmountDue(totalAmount);
        bill.setPaymentDone(false);

        // ✅ Apply Discount
        if (request.getDiscount() != null) {
            Discount discount = new Discount();
            discount.setDiscountAmount(request.getDiscount().getDiscountAmount());
            discount.setAuthorizedBy(request.getDiscount().getAuthorizedBy());
            discount.setCategories(request.getDiscount().getCategories());
            discountRepository.save(discount);
            bill.setDiscount(discount);

            // Subtract discount from total amount
            bill.setAmountDue(totalAmount - request.getDiscount().getDiscountAmount());
        }

        // ✅ Apply Insurance
        if (request.getInsurance() != null) {
            InsuranceOption insurance = new InsuranceOption();
            insurance.setInsuranceProviderName(request.getInsurance().getInsuranceProviderName());
            insurance.setInsurancePaid(request.getInsurance().getInsurancePaid());
            insuranceRepository.save(insurance);
            bill.setInsurance(insurance);

            // Subtract insurance from total amount
            bill.setAmountDue(bill.getAmountDue() - request.getInsurance().getInsurancePaid());
        }

        // Ensure Amount Due is not negative
        if (bill.getAmountDue() < 0) {
            bill.setAmountDue(0.0);
        }

        // ✅ Save Bill
        Bill savedBill = billRepository.save(bill);
        log.info("Bill generated with ID: {} | Amount Due: {}" , savedBill.getId() , savedBill.getAmountDue());

        return new PharmacyBillResponse(
                savedBill.getId() ,
                savedBill.getTotalAmount() ,
                request.getDiscount() != null?request.getDiscount().getDiscountAmount():0.0 ,
                request.getInsurance() != null?request.getInsurance().getInsurancePaid():0.0 ,
                savedBill.getAmountDue() ,
                savedBill.getPaymentDone() ,
                "Bill generated successfully. Please proceed to payment."
        );
    }


    @Transactional
    public PharmacyPaymentResponse makePayment ( PharmacyPaymentRequest request ) {
        log.info("Processing payment for Bill ID: {}" , request.getBillId());

        // ✅ Validate Bill
        Bill bill = billRepository.findById(request.getBillId())
                                  .orElseThrow(() -> new BillNotFoundException(
                                          "Bill not found with ID: " + request.getBillId()));

        // ✅ Validate Payment
        if (bill.getPaymentDone()) {
            throw new IllegalStateException("Payment has already been made for this bill.");
        }

        // ✅ Update Payment Details
        bill.setAmountPaid(request.getAmountPaid());
        bill.setPaymentMethod(request.getPaymentMethod());
        bill.setTransactionId(request.getTransactionId());

        // ✅ Check if Full Payment is Done
        double remainingAmount = bill.getTotalAmount() - request.getAmountPaid();
        if (remainingAmount <= 0) {
            bill.setPaymentDone(true);
            bill.setAmountDue(0.0);
        } else {
            bill.setAmountDue(remainingAmount);
        }

        // ✅ If payment is completed, reduce stock of medicines
        if (bill.getPaymentDone()) {
            SlotBooking booking = bill.getBooking();
            List<Medicine> medicines = booking.getPrescription() != null
                    ?booking.getPrescription().getMedicines()
                    :new ArrayList<>();

            for (Medicine med : medicines) {
                if (med.getStockQuantity() > 0) {
                    med.setStockQuantity(med.getStockQuantity() - 1); // Deduct 1 per dispense
                } else {
                    throw new OutOfStockException("Medicine " + med.getName() + " is out of stock!");
                }
            }
            medicineRepository.saveAll(medicines);
        }

        // ✅ Save Bill with Updated Payment
        Bill updatedBill = billRepository.save(bill);
        log.info("Payment successful for Bill ID: {}" , updatedBill.getId());

        return new PharmacyPaymentResponse(
                updatedBill.getId() ,
                updatedBill.getTotalAmount() ,
                updatedBill.getAmountPaid() ,
                updatedBill.getAmountDue() ,
                updatedBill.getPaymentDone() ,
                "Payment processed successfully!"
        );
    }
}

