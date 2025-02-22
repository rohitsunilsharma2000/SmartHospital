package com.hms.service;

import com.hms.dto.BillRequest;
import com.hms.dto.BillResponse;
import com.hms.dto.PdfBillDTO;
import com.hms.dto.PdfBillItemDTO;
import com.hms.enums.SlotStatus;
import com.hms.exception.BillNotFoundException;
import com.hms.exception.BookingNotFoundException;
import com.hms.exception.DuplicateBillException;
import com.hms.exception.InvalidSlotStatusException;
import com.hms.modal.*;
import com.hms.repository.BillRepository;
import com.hms.repository.SlotAvailabilityRepository;
import com.hms.repository.SlotBookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillService {

    private final BillRepository billRepository;
    private final SlotBookingRepository slotBookingRepository;
    private final SlotAvailabilityRepository slotAvailabilityRepository;

    // ✅ Create Bill
    @Transactional(rollbackFor = Exception.class)
    public BillResponse saveBill(Long billId, BillRequest request) {
        boolean isUpdate = (billId != null);
        log.info("{} bill with booking ID: {}", isUpdate ? "Updating" : "Creating", request.getBookingId());

        Bill bill = getOrCreateBill(billId, request, isUpdate);

        setBillDetails(bill, request);
        updateSlotStatus(bill, request, isUpdate);

        Bill savedBill = billRepository.save(bill);
        log.info("Bill {} successfully with ID: {}", isUpdate ? "updated" : "created", savedBill.getId());

        return mapToResponse(savedBill);
    }


    private Bill getOrCreateBill(Long billId, BillRequest request, boolean isUpdate) {
        SlotBooking booking = validateBookingById(request.getBookingId()); // ✅ Check Booking ID

        if (isUpdate) {
            // ✅ Update existing Bill
            Bill bill = billRepository.findById(billId)
                                      .orElseThrow(() -> {
                                          log.error("Bill not found with ID: {}", billId);
                                          return new BillNotFoundException("Bill not found with ID: " + billId);
                                      });

            if (billRepository.existsByBookingIdAndIdNot(request.getBookingId(), billId)) {
                log.error("Another bill already exists for booking ID: {}", request.getBookingId());
                throw new DuplicateBillException("Another bill already exists for this booking");
            }
            return bill;

        } else {
            // ✅ Create new Bill if Booking is valid
            if (billRepository.existsByBookingId(request.getBookingId())) {
                log.error("Bill already exists for booking ID: {}", request.getBookingId());
                throw new DuplicateBillException("Bill already exists for this booking");
            }

            Bill bill = new Bill();
            bill.setBooking(booking);
            return bill;
        }
    }

    // ✅ Method to Check if Booking Exists by Booking ID
    private SlotBooking validateBookingById(Long bookingId) {
        return slotBookingRepository.findById(bookingId)
                                    .orElseThrow(() -> {
                                        log.error("Booking not found with ID: {}", bookingId);
                                        return new BookingNotFoundException("Booking not found with ID: " + bookingId);
                                    });
    }

    private void setBillDetails(Bill bill, BillRequest request) {
        bill.setTotalAmount(request.getTotalAmount());
        bill.setAmountPaid(request.getAmountPaid());
        bill.setPaymentMethod(request.getPaymentMethod());
        bill.setTransactionId(request.getTransactionId());
        bill.setPaymentDone(request.getPaymentDone());

        setOrUpdateDiscount(bill, request);
        setOrUpdateInsurance(bill, request);
    }

    private void setOrUpdateDiscount(Bill bill, BillRequest request) {
        if (request.getDiscount() != null) {
            if (bill.getDiscount() != null) {
                Discount discount = bill.getDiscount();
                discount.setDiscountAmount(request.getDiscount().getDiscountAmount());
                discount.setAuthorizedBy(request.getDiscount().getAuthorizedBy());
                discount.setCategories(request.getDiscount().getCategories());
            } else {
                bill.setDiscount(request.getDiscount());
            }
        } else {
            bill.setDiscount(null);
        }
    }
    private void setOrUpdateInsurance(Bill bill, BillRequest request) {
        if (request.getInsurance() != null) {
            if (bill.getInsurance() != null) {
                InsuranceOption insurance = bill.getInsurance();
                insurance.setInsuranceProviderName(request.getInsurance().getInsuranceProviderName());
                insurance.setInsurancePaid(request.getInsurance().getInsurancePaid());
                insurance.setPolicyId(request.getInsurance().getPolicyId());
                insurance.setExpiryDate(request.getInsurance().getExpiryDate());
                insurance.setPolicyHolder(request.getInsurance().getPolicyHolder());
            } else {
                bill.setInsurance(request.getInsurance());
            }
        } else {
            bill.setInsurance(null);
        }
    }
    private void updateSlotStatus(Bill bill, BillRequest request, boolean isUpdate) {
        if (Boolean.TRUE.equals(request.getPaymentDone()) && !isUpdate) {
            SlotAvailability slot = bill.getBooking().getSlot();
            if (slot.getStatus() == SlotStatus.BOOKED) {
                log.info("Before update: Slot status is {}", slot.getStatus());
                slot.setStatus(SlotStatus.ARRIVED);
                slotAvailabilityRepository.save(slot);
                log.info("After update: Slot status is ARRIVED for slotNumber: {}", slot.getSlotNumber());
            } else {
                log.warn("Slot cannot be marked as ARRIVED unless it's currently BOOKED");
                throw new InvalidSlotStatusException("Slot cannot be marked as ARRIVED unless it's currently BOOKED");
            }
        }
    }


    // ✅ Get All Bills
    public List<BillResponse> getAllBills() {
        return billRepository.findAll().stream()
                             .map(this::mapToResponse)
                             .toList();

    }

    // ✅ Get Bill by ID
    public BillResponse getBillById(Long id) {
        Bill bill = billRepository.findById(id)
                                  .orElseThrow(() -> new RuntimeException("Bill not found"));
        return mapToResponse(bill);
    }

    // ✅ Search Bills by Multiple Params
    public List<BillResponse> searchBills(String keyword, Boolean paymentDone) {
        return billRepository.findByBookingPatientFirstNameContainingIgnoreCaseOrBookingDoctorNameContainingIgnoreCaseOrBookingSlotSlotNumberContainingIgnoreCaseOrTransactionIdContainingIgnoreCaseOrPaymentDone(
                                     keyword, keyword, keyword, keyword, paymentDone)
                             .stream()
                             .map(this::mapToResponse)
                             .toList();
    }

    // ✅ Delete Bill
    @Transactional
    public void deleteBill(Long billId) {
        Bill bill = billRepository.findById(billId)
                                  .orElseThrow(() -> new BillNotFoundException("Bill not found with ID: " + billId));

        billRepository.delete(bill); // ✅ Automatically deletes associated InsuranceOption and Discount
        log.info("Bill deleted successfully with ID: {}", billId);
    }


    // ✅ Map Entity to Response
    private BillResponse mapToResponse( Bill bill) {
        BillResponse response = new BillResponse();
        response.setId(bill.getId());
        response.setPatientName(bill.getBooking().getPatient().getFirstName() + " " + bill.getBooking().getPatient().getLastName());
        response.setDoctorName(bill.getBooking().getDoctor().getName());
        response.setSlotNumber(bill.getBooking().getSlot().getSlotNumber());
        response.setTotalAmount(bill.getTotalAmount());
        response.setAmountPaid(bill.getAmountPaid());
        response.setPaymentMethod(bill.getPaymentMethod());
        response.setTransactionId(bill.getTransactionId());
        response.setPaymentDone(bill.getPaymentDone());

        if (bill.getDiscount() != null) {
            response.setDiscountAmount(bill.getDiscount().getDiscountAmount());
            response.setDiscountAuthorizedBy(bill.getDiscount().getAuthorizedBy());
        }

        if (bill.getInsurance() != null) {
            response.setInsuranceProviderName(bill.getInsurance().getInsuranceProviderName());
            response.setInsurancePaid(bill.getInsurance().getInsurancePaid());
        }

        return response;
    }

    /**
     * Fetch Bill data along with related SlotBooking, Doctor, Patient, Insurance, and Discount
     *
     * @param billId Bill ID
     * @return PdfBillDTO
     */
    public PdfBillDTO getBillDetails( Long billId) {
//        // ✅ Fetch Bill
        Bill bill = billRepository.findById(billId)
                                  .orElseThrow(() -> new BillNotFoundException("Bill not found"));


        // ✅ Check if the Bill exists using existsById()
//        if (!billRepository.existsById(billId)) {
//            log.error("Bill with ID {} not found", billId);
//            throw new BillNotFoundException("Bill with ID " + billId + " not found");
//        }
//
//        // ✅ Retrieve the Bill since it's guaranteed to exist
//        Bill bill = billRepository.findById(billId).get();



        SlotBooking booking = bill.getBooking();
        SlotAvailability slot = booking.getSlot();
        Doctor doctor = booking.getDoctor();
        Patient patient = booking.getPatient();

        // ✅ Format date
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a"); // 12-hour format with AM/PM

        // Extract start time from "9:40 am to 9:50 am"
        String startTime = slot.getTime().split(" to ")[0].trim();

        // Parse Date
        LocalDateTime dateTime = LocalDateTime.of(
                LocalDate.parse(slot.getStartDate(), dateFormatter),
                LocalTime.parse(startTime, timeFormatter) // ✅ Only parse the start time
        );

        // ✅ Construct PdfBillDTO
        PdfBillDTO billDTO = new PdfBillDTO();
        billDTO.setClinicName("Nirmal Clinic");
        billDTO.setClinicAddress("#416/A, 2nd Floor, 11th B Cross, 1st Phase, JP Nagar, Bangalore, 560078");
        billDTO.setPhoneNumber("9591027967");
        billDTO.setEmail("lohith155@gmail.com");
        billDTO.setWebsite("nirmalclinic.in");
        billDTO.setIvrPhone("08046972434");

        billDTO.setPatientName(patient.getFirstName() + " " + patient.getLastName());
        billDTO.setContactNumber(patient.getId().getMobile());
        billDTO.setPatientId(patient.getId().getHospId());
        billDTO.setAgeSex(patient.getAge() + " YRS / " + patient.getSex());
        billDTO.setDateTime(dateTime);
        billDTO.setBillNumber(bill.getId().toString());
        billDTO.setDoctorName(doctor.getName());

        // ✅ Generate Items List
        List<PdfBillItemDTO> items = generateItems(bill);
        billDTO.setItems(items);

        // ✅ Total Billed
        double totalBilled = bill.getTotalAmount();
        billDTO.setTotalBilled(totalBilled);

        // ✅ Discount
        double discountAmount = bill.getDiscount() != null ? bill.getDiscount().getDiscountAmount() : 0.0;
        billDTO.setDiscount(discountAmount);

        // ✅ Insurance Paid
        double insuranceAmount = bill.getInsurance() != null ? bill.getInsurance().getInsurancePaid() : 0.0;
        billDTO.setInsurancePaid(insuranceAmount);

        // ✅ Final Payable Amount
        double finalPayableAmount = Math.max(totalBilled - insuranceAmount, 0.00);
        billDTO.setFinalPayableAmount(finalPayableAmount);

        // ✅ Amount Paid
        billDTO.setAmountPaid(bill.getAmountPaid());
        billDTO.setAmountInWords(convertAmountToWords(bill.getAmountPaid()));

        billDTO.setReceiptNumber("OP-" + bill.getId() + "-1");
        billDTO.setPaymentMode(bill.getPaymentMethod());

        billDTO.setTokenNumber(7);  // Placeholder value
        billDTO.setQueue(doctor.getName());
        billDTO.setRoom("105"); // Placeholder value

        billDTO.setAuthorizedSignatory(doctor.getName());

        return billDTO;
    }

    /**
     * Generate Bill Items
     *
     * @param bill Bill
     * @return List<PdfBillItemDTO>
     */
    private List<PdfBillItemDTO> generateItems(Bill bill) {
        int serialNumber = 1;

        // ✅ Consultation Fee
        PdfBillItemDTO consultationItem = new PdfBillItemDTO();
        consultationItem.setSerialNumber(serialNumber++);
        consultationItem.setParticulars("Consultation - " + bill.getBooking().getDoctor().getName());
        consultationItem.setCharges(bill.getTotalAmount());
        consultationItem.setQuantity(1);
        consultationItem.setAmount(bill.getTotalAmount());

        // ✅ Discount Item
        PdfBillItemDTO discountItem = null;
        if (bill.getDiscount() != null) {
            discountItem = new PdfBillItemDTO();
            discountItem.setSerialNumber(serialNumber++);
            discountItem.setParticulars("Discount (Authorized by: " + bill.getDiscount().getAuthorizedBy() + ")");
            discountItem.setCharges(-bill.getDiscount().getDiscountAmount());
            discountItem.setQuantity(1);
            discountItem.setAmount(-bill.getDiscount().getDiscountAmount());
        }

        // ✅ Insurance Item
        PdfBillItemDTO insuranceItem = null;
        if (bill.getInsurance() != null) {
            insuranceItem = new PdfBillItemDTO();
            insuranceItem.setSerialNumber(serialNumber++);
            insuranceItem.setParticulars("Insurance (" + bill.getInsurance().getInsuranceProviderName() + ")");
            insuranceItem.setCharges(-bill.getInsurance().getInsurancePaid());
            insuranceItem.setQuantity(1);
            insuranceItem.setAmount(-bill.getInsurance().getInsurancePaid());
        }


        return Stream.of(Optional.ofNullable(consultationItem),
                         Optional.ofNullable(discountItem),
                         Optional.ofNullable(insuranceItem))
                     .flatMap(Optional::stream) // ✅ Extracts non-null values
                     .toList();


    }

    /**
     * Convert amount to words
     *
     * @param amount Double
     * @return String
     */
    private String convertAmountToWords(double amount) {
        if (amount == 0) return "Zero Only";

        long wholePart = (long) amount; // Integer part
        int decimalPart = (int) Math.round((amount - wholePart) * 100); // Decimal part (cents/paise)

        StringBuilder result = new StringBuilder();

        // Convert whole number
        result.append(convertNumberToWords(wholePart));

        // Append "Only" if no decimal part
        if (decimalPart == 0) {
            result.append(" Only");
        } else {
            result.append(" and ").append(convertNumberToWords(decimalPart)).append(" Cents Only");
        }

        return result.toString();
    }
    private String convertNumberToWords(long number) {
        if (number == 0) return "Zero";

        final String[] units = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
                "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen",
                "Seventeen", "Eighteen", "Nineteen"};

        final String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

        StringBuilder words = new StringBuilder();

        // Handle Billions
        if (number / 1_000_000_000 > 0) {
            words.append(convertNumberToWords(number / 1_000_000_000)).append(" Billion ");
            number %= 1_000_000_000;
        }

        // Handle Millions
        if (number / 1_000_000 > 0) {
            words.append(convertNumberToWords(number / 1_000_000)).append(" Million ");
            number %= 1_000_000;
        }

        // Handle Thousands
        if (number / 1_000 > 0) {
            words.append(convertNumberToWords(number / 1_000)).append(" Thousand ");
            number %= 1_000;
        }

        // Handle Hundreds
        if (number / 100 > 0) {
            words.append(convertNumberToWords(number / 100)).append(" Hundred ");
            number %= 100;
        }

        // Handle units and tens
        if (number > 0) {
            if (number < 20) {
                words.append(units[(int) number]);
            } else {
                words.append(tens[(int) number / 10]);
                if ((number % 10) > 0) {
                    words.append("-").append(units[(int) number % 10]);
                }
            }
        }

        return words.toString().trim();
    }



}
