package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "consultation_fees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    private String consultationType; // E-Consultation, Video, Follow-Up, First-Time, etc.
    private Double unitPrice;
}
