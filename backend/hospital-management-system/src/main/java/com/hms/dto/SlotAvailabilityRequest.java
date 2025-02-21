package com.hms.dto;

import lombok.Data;

import java.util.List;

@Data
public class SlotAvailabilityRequest {
    private String days;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private List<SubSlotDTO> subSlots;
}
