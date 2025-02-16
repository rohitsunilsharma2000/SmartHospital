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
public class PastHistory {
    private Boolean jaundice;
    private Boolean bloodTransfusions;
    private Boolean asthmaOrCopd;
    private Boolean tuberculosis;
    private Boolean thyroidProblem;
}
