package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "follow_ups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Follow up duration stored as YMD (e.g., "0Y 1M 0D")
    private String followUpDurationYMD;

    // Date of follow up visit
    private LocalDateTime followUpDate;

    // Tests prescribed specifically for the follow-up visit (using the PrescribedTest mapping)
    @OneToMany(mappedBy = "followUp", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrescribedTest> followUpTests;

    // List of advices (for example: "Walk daily for 30 minutes", etc.)
    @ElementCollection
    @CollectionTable(name = "followup_advices", joinColumns = @JoinColumn(name = "followup_id"))
    @Column(name = "advice")
    private List<String> advices;

    // Referred to (if any)
    private String referredTo;
}
