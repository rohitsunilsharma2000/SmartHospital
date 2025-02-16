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
public class TargetOrganInvolvement {
    // For eyes – each field could be later switched to an enum if needed.
    private String eyesRetinopathy;          // e.g., "No", "ONPDR", "EPOR", "OVH"
    private String eyesLaserPhotocoagulation;  // e.g., "NO", "ORE", "OLE", "Q Bilateral"
    private String eyesVision;               // e.g., "Good", "Impaired", "Q Blind"
}
