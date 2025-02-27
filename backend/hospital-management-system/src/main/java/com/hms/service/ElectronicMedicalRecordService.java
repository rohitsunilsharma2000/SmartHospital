package com.hms.service;

import com.hms.dto.ElectronicMedicalRecordRequest;
import com.hms.exception.BookingNotFoundException;
import com.hms.exception.EMRAlreadyExistsException;
import com.hms.modal.*;
import com.hms.repository.*;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElectronicMedicalRecordService {

    private final ElectronicMedicalRecordRepository emrRepository;
    private final SlotBookingRepository slotBookingRepository;
    private final PrescriptionRepository prescriptionRepository;

    private final PrescribedTestRepository prescribedTestRepository;
    private final FollowUpRepository followUpRepository;
    private final PrescribedMedicineRepository prescribedMedicineRepository;


    private final ElectronicMedicalRecordRepository electronicMedicalRecordRepository;

    // ✅ Create or Update EMR
    @Transactional
    public String saveOrUpdateEMR(ElectronicMedicalRecordRequest request) {
        boolean isUpdate = (request.getId() != null);
        log.info("{} Electronic Medical Record for Booking ID: {}", isUpdate ? "Updating" : "Creating", request.getBookingId());

        // ✅ Validate Slot Booking
        SlotBooking booking = slotBookingRepository.findById(request.getBookingId())
                                                   .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + request.getBookingId()));

        ElectronicMedicalRecord emr;

        if (isUpdate) {
            emr = electronicMedicalRecordRepository.findById(request.getId())
                                                   .orElseThrow(() -> new EntityNotFoundException("EMR not found with ID: " + request.getId()));
        } else {
            if (electronicMedicalRecordRepository.existsBySlotBookingId(request.getBookingId())) {
                throw new EMRAlreadyExistsException("EMR already exists for this booking");
            }
            emr = new ElectronicMedicalRecord();
        }

        // ✅ Set Prescription
        if (request.getPrescription() != null) {
            Prescription prescription = request.getPrescription();
            if (prescription.getPrescribedMedicines() != null) {
                for (PrescribedMedicine medicine : prescription.getPrescribedMedicines()) {
                    medicine.setPrescription(prescription); // ✅ Link medicine to prescription
                }
            }
            emr.setPrescription(prescription);
        } else {
            emr.setPrescription(null);
        }

        // ✅ Handle Prescribed Tests without Orphan Issues
        if (request.getPrescribedTests() != null) {
            mergePrescribedTests(emr, request.getPrescribedTests());
        } else {
            emr.getPrescribedTests().clear(); // ✅ Remove tests if request contains null
        }

        emr.setSlotBooking(booking);
        electronicMedicalRecordRepository.save(emr);

        log.info("{} Electronic Medical Record successfully for Booking ID: {}", isUpdate ? "Updated" : "Created", request.getBookingId());
        return isUpdate ? "Electronic Medical Record updated successfully." : "Electronic Medical Record created successfully.";
    }




    // ✅ Retrieve EMR by Booking ID
    public ElectronicMedicalRecord getEMRByBookingId ( Long bookingId ) {
        return electronicMedicalRecordRepository.findBySlotBookingId(bookingId)
                                                .orElseThrow(() -> new EntityNotFoundException(
                                                        "EMR not found for Booking ID: " + bookingId));
    }

    // ✅ Delete EMR by ID
    @Transactional
    public void deleteEMR ( Long emrId ) {
        if (!electronicMedicalRecordRepository.existsById(emrId)) {
            throw new EntityNotFoundException("EMR not found with ID: " + emrId);
        }
        electronicMedicalRecordRepository.deleteById(emrId);
    }

    private void mergePrescribedTests(ElectronicMedicalRecord emr, List<PrescribedTest> newTests) {
        if (emr.getPrescribedTests() == null) {
            emr.setPrescribedTests(new ArrayList<>());
        }

        Map<Long, PrescribedTest> existingTests = emr.getPrescribedTests()
                                                     .stream().filter(test -> test.getId() != null)
                                                     .collect(Collectors.toMap(PrescribedTest::getId, test -> test));

        List<PrescribedTest> mergedTests = new ArrayList<>();

        for (PrescribedTest newTest : newTests) {
            if (newTest.getId() != null && existingTests.containsKey(newTest.getId())) {
                // ✅ Update existing test
                PrescribedTest existingTest = existingTests.get(newTest.getId());
                existingTest.setTestName(newTest.getTestName());
                existingTest.setQuantity(newTest.getQuantity());
                existingTest.setNoteOrInstructions(newTest.getNoteOrInstructions());
                mergedTests.add(existingTest);
            } else {
                // ✅ New test, assign EMR reference
                newTest.setElectronicMedicalRecord(emr);
                mergedTests.add(newTest);
            }
        }

        // ✅ Remove missing tests
        emr.getPrescribedTests().clear();
        emr.getPrescribedTests().addAll(mergedTests);
    }

}


