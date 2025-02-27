package com.hms.modal;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChiefComplaint {
    private String complaint;
    private String duration; // e.g., "2Y 3M 1W 4D"
    private String bodyPart;
}
