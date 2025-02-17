package com.hms.dto;

import com.hms.enums.SlotStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public
class SlotResponse {
    private Long slotId;
    private String startTime;
    private String endTime;
    private SlotStatus status;

    public SlotStatus getStatus() {
        return status != null ? status : SlotStatus.AVAILABLE; // Default value handling
    }
}
