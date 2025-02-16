package com.hms.modal;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalHistory {

    // History of present illness
    private String historyOfPresentIllness;

    // Past history (as a nested embeddable)
    @Embedded
    private PastHistory pastHistory;

    // Diabetes and its complications (nested embeddable)
    @Embedded
    private DiabetesComplications diabetesComplications;

    // Target organ involvement for diabetes complications
    @Embedded
    private TargetOrganInvolvement targetOrganInvolvement;

    // Renal history – stored as a list of options (e.g., "Edema Legs", "Kidney Stones", etc.)
    @ElementCollection
    @CollectionTable(name = "emr_renal_history", joinColumns = @JoinColumn(name = "emr_id"))
    @Column(name = "renal_history_item")
    private List<String> renalHistory;
}
