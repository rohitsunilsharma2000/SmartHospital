package com.hms.modal;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientId implements Serializable {

    private String mobile; // Composite key field 1
    private String hospId; // Composite key field 2
}
