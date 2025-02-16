package com.hms.modal;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalHistory {
    // Use strings (or enums) such as "Yes", "No", "NA"
    private String smoking;
    private String alcoholUse;
    private String drugAbuse;
    private String tattooMarks;
    private String exercise;
    // Diet options, e.g., "Non Veg" or "Veg"
    private String diet;
    // Number of children
    private Integer noOfChildren;
}
