package com.hms.modal;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FollowUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Follow up duration stored as YMD (e.g., "0Y 1M 0D")
    private String followUpDurationYMD;

    // Date of follow up visit
    private LocalDateTime followUpDate;

    // Tests prescribed specifically for the follow-up visit (using the PrescribedTest mapping)

//    private List<PrescribedTest> followUpTests;

    // List of advices (for example: "Walk daily for 30 minutes", etc.)


    private List<String> advices;

    // Referred to (if any)
    private String referredTo;
}