package com.hms.service.impl;

import com.hms.api.DoctorResponse;
import com.hms.dto.AvailabilityResponse;
import com.hms.dto.CreateDoctorRequest;
import com.hms.dto.DoctorSlotResponse;
import com.hms.dto.SlotResponse;
import com.hms.enums.SlotStatus;
import com.hms.exception.DoctorAlreadyExistsException;
import com.hms.exception.SlotNotFoundException;
import com.hms.exception.SlotReservationAlreadyExistsException;
import com.hms.modal.Availability;
import com.hms.modal.Doctor;
import com.hms.modal.SlotReservation;
import com.hms.repository.AvailabilityRepository;
import com.hms.repository.DoctorRepository;
import com.hms.repository.SlotReservationRepository;
import com.hms.service.DoctorService;
import com.hms.service.DoctorSlotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorSlotService doctorSlotService;
    private final AvailabilityRepository availabilityRepository;
    private final SlotReservationRepository slotReservationRepository;

    public DoctorServiceImpl ( DoctorRepository doctorRepository , DoctorSlotService doctorSlotService , AvailabilityRepository availabilityRepository , SlotReservationRepository slotReservationRepository ) {
        this.doctorRepository = doctorRepository;
        this.doctorSlotService = doctorSlotService;
        this.availabilityRepository = availabilityRepository;
        this.slotReservationRepository = slotReservationRepository;
    }

    @Override
    @Transactional
    public Doctor registerDoctor(CreateDoctorRequest request, String startTime, String endTime, List<String> days, List<String> dates) {
        // ✅ Check if doctor with the same name and department already exists
        if (doctorRepository.existsByNameAndDepartment(request.getName(), request.getDepartment())) {
            throw new DoctorAlreadyExistsException("Doctor with name " + request.getName() + " and department " + request.getDepartment() + " already exists");
        }

        // ✅ Register doctor
        Doctor doctor = Doctor.builder()
                              .name(request.getName())
                              .department(request.getDepartment())
                              .outPatient(request.getOutPatient())
                              .busyTime(request.getBusyTime())
                              .notificationSchedules(request.getNotificationSchedules())
                              .build();

        doctor = doctorRepository.save(doctor);

        // ✅ Generate Slots for Doctor
        List<DoctorSlotResponse> slots = doctorSlotService.generateOrUpdateDoctorSlots(doctor.getId(), startTime, endTime, days, dates);

        // ✅ Track Slot Reservations for the Doctor
        for (DoctorSlotResponse slotResponse : slots) {
            for (SlotResponse slot : slotResponse.getSlots()) {
                Availability slotEntity = availabilityRepository.findById(slot.getSlotId())
                                                                .orElseThrow(() -> new SlotNotFoundException("Slot with ID " + slot.getSlotId() + " not found"));

                // ✅ Check for duplicate slot reservation before saving
                if (!slotReservationRepository.existsByDoctorAndSlot(doctor, slotEntity)) {
                    SlotReservation reservation = SlotReservation.builder()
                                                                 .doctor(doctor)
                                                                 .slot(slotEntity)
                                                                 .status(SlotStatus.AVAILABLE)
                                                                 .reservationTime(LocalDateTime.now())
                                                                 .notes("Doctor's availability slot created.")
                                                                 .build();

                    slotReservationRepository.save(reservation);
                } else {
                    throw new SlotReservationAlreadyExistsException("Slot reservation for doctor " + doctor.getName() + " and slot " + slotEntity.getId() + " already exists");
                }
            }
        }

        return doctor;
    }

    @Transactional

    public Doctor registerDoctorV2 ( CreateDoctorRequest request , String startTime , String endTime , List<String> days , List<String> dates ) {
        // Register doctor
        Doctor doctor = Doctor.builder()
                              .name(request.getName())
                              .department(request.getDepartment())
                              .outPatient(request.getOutPatient())
                              .busyTime(request.getBusyTime())
                              .notificationSchedules(request.getNotificationSchedules())
                              .build();

        doctor = doctorRepository.save(doctor);

        // Generate Slots for Doctor
        List<DoctorSlotResponse> slots = doctorSlotService.generateOrUpdateDoctorSlots(doctor.getId() , startTime ,
                                                                                       endTime , days , dates);

        // Track Slot Reservations for the Doctor
        for (DoctorSlotResponse slotResponse : slots) {
            for (SlotResponse slot : slotResponse.getSlots()) {
                Availability slotEntity = availabilityRepository.findById(slot.getSlotId())
                                                                .orElseThrow(
                                                                        () -> new RuntimeException("Slot not found"));

                SlotReservation reservation = SlotReservation.builder()
                                                             .doctor(doctor)
                                                             .slot(slotEntity)
                                                             .status(SlotStatus.AVAILABLE)
                                                             .reservationTime(LocalDateTime.now())
                                                             .notes("Doctor's availability slot created.")
                                                             .build();

                slotReservationRepository.save(reservation);
            }
        }

        return doctor;
    }


    @Override
    @Transactional
    public List<Doctor> registerMultipleDoctors(List<CreateDoctorRequest> requests, String startTime, String endTime, List<String> days, List<String> dates) {

        List<Doctor> doctors = requests.stream()
                                       .map(request -> {
                                           // ✅ Check if doctor already exists
                                           if (doctorRepository.existsByNameAndDepartment(request.getName(), request.getDepartment())) {
                                               throw new DoctorAlreadyExistsException("Doctor with name " + request.getName() + " and department " + request.getDepartment() + " already exists");
                                           }

                                           // ✅ Register doctor
                                           Doctor doctor = Doctor.builder()
                                                                 .name(request.getName())
                                                                 .department(request.getDepartment())
                                                                 .outPatient(request.getOutPatient())
                                                                 .busyTime(request.getBusyTime())
                                                                 .notificationSchedules(request.getNotificationSchedules())
                                                                 .build();

                                           return doctorRepository.save(doctor);
                                       })
                                       .toList();

        // ✅ Generate Slots & Track Reservations for Each Doctor
        doctors.forEach(doctor -> {
            List<DoctorSlotResponse> slots = doctorSlotService.generateOrUpdateDoctorSlots(doctor.getId(), startTime, endTime, days, dates);

            for (DoctorSlotResponse slotResponse : slots) {
                for (SlotResponse slot : slotResponse.getSlots()) {

                    // ✅ Fetch Slot
                    Availability slotEntity = availabilityRepository.findById(slot.getSlotId())
                                                                    .orElseThrow(() -> new SlotNotFoundException("Slot with ID " + slot.getSlotId() + " not found"));

                    // ✅ Check if Slot Reservation Already Exists
                    boolean reservationExists = slotReservationRepository.existsByDoctorIdAndSlotId(doctor.getId(), slotEntity.getId());
                    if (reservationExists) {
                        throw new SlotReservationAlreadyExistsException("Slot reservation for doctor " + doctor.getName() + " and slot " + slotEntity.getId() + " already exists");
                    }

                    // ✅ Create Slot Reservation
                    SlotReservation reservation = SlotReservation.builder()
                                                                 .doctor(doctor)
                                                                 .slot(slotEntity)
                                                                 .status(SlotStatus.AVAILABLE)
                                                                 .reservationTime(LocalDateTime.now())
                                                                 .notes("Doctor's availability slot created.")
                                                                 .build();

                    slotReservationRepository.save(reservation);
                }
            }
        });

        return doctors;
    }


    // 1. Get All Doctors
    @Override

    public List<DoctorResponse> getAllDoctorsWithSlots() {
        List<Doctor> doctors = doctorRepository.findAllWithAvailabilities();
        return mapToDoctorResponseList(doctors);
    }

    // 2. Get Doctor by ID
    @Override

    public DoctorResponse getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findByIdWithAvailabilities(id)
                                        .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
        return mapToDoctorResponse(doctor);
    }

    // 3. Get Doctor by Name
    @Override
    public List<DoctorResponse> searchDoctorsByName(String query) {
        List<Doctor> doctors = doctorRepository.findByNameContainingIgnoreCase(query);
        return doctors.stream().map(this::mapToDoctorResponse).collect(Collectors.toList());
    }

    @Override
    public List<DoctorResponse> searchDoctorsById(String query) {
        // Validate input (ensure query is numeric)
        if (!query.matches("\\d+")) {
            throw new IllegalArgumentException("Query must be numeric");
        }

        List<Doctor> doctors = doctorRepository.findByIdStartingWith(query);

        // Map entity to response DTO
        return doctors.stream()
                      .map(this::mapToDoctorResponse)
                      .collect(Collectors.toList());
    }


    // Utility Methods
    private List<DoctorResponse> mapToDoctorResponseList(List<Doctor> doctors) {
        return doctors.stream().map(this::mapToDoctorResponse).collect(Collectors.toList());
    }

    private DoctorResponse mapToDoctorResponse(Doctor doctor) {
        Map<String, List<AvailabilityResponse>> slotsGroupedByHour = doctor.getAvailabilities().stream()
                                                                           .collect(Collectors.groupingBy(
                                                                                   availability -> availability.getStartTime().substring(0, 5),
                                                                                   LinkedHashMap::new,
                                                                                   Collectors.mapping(availability -> AvailabilityResponse.builder()
                                                                                                                                          .slotId(availability.getId())
                                                                                                                                          .startTime(availability.getStartTime())
                                                                                                                                          .endTime(availability.getEndTime())
                                                                                                                                          .status(availability.getStatus().name())
                                                                                                                                          .build(), Collectors.toList())
                                                                           ));

        return DoctorResponse.builder()
                             .doctorId(doctor.getId())
                             .doctorName(doctor.getName())
                             .department(doctor.getDepartment())
                             .slots(slotsGroupedByHour)
                             .build();
    }

}
