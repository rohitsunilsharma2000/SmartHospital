package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // e.g., "Paediatrics" – you could also use an enum for department if you wish.
    private String department;

    // For example, a flag that indicates if the doctor is outpatient ("OUT")
    private Boolean outPatient;

    // A string field to represent busy time (if more details are needed you might use another entity)
    private String busyTime;

    /**
     * A list of notification schedules (e.g., "Hourly", "Daily", "Weekly").
     * This example uses an ElementCollection to store a list of strings.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "doctor_notification_schedules", joinColumns = @JoinColumn(name = "doctor_id"))
    @Column(name = "schedule")
    private List<String> notificationSchedules;

    // One doctor can have many availabilities (time slots).
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Availability> availabilities;
}
