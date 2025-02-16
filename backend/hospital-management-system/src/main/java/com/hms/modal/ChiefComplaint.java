package com.hms.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiefComplaint {
    // The complaint text (could be chosen from an MDM fetch list)
    private String complaint;
    // Duration stored as a string (e.g. "2Y 3M 1W 4D")
    private String duration;
    // Body part where the complaint is located
    private String bodyPart;
}
