package com.hms.dto;

import lombok.Data;

@Data
public class CreateAvailabilityRequest {
    private String startTime;
    private String endTime;
    private String consultationType;
    // Optionally, you can pass doctorId here. In our implementation we use the URL path.
}
