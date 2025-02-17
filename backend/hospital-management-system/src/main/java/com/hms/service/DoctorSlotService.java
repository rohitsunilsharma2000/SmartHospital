package com.hms.service;


import com.hms.dto.DoctorSlotResponse;
import com.hms.enums.SlotStatus;

import java.util.List;

public interface DoctorSlotService {
   List<DoctorSlotResponse> generateOrUpdateDoctorSlots(Long doctorId, String startTimeStr, String endTimeStr, List<String> days, List<String> dates) ;

    void updateSlotStatus(Long slotId, SlotStatus newStatus);
}
