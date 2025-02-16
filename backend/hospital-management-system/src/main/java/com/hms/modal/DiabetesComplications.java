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
public class DiabetesComplications {
    // Lists of details about diabetes duration, glycemic control, and hyperglycemic episodes
    @ElementCollection
    @CollectionTable(name = "emr_dm_durations", joinColumns = @JoinColumn(name = "emr_id"))
    @Column(name = "dm_duration")
    private List<String> dmDurations;

    @ElementCollection
    @CollectionTable(name = "emr_glycemic_controls", joinColumns = @JoinColumn(name = "emr_id"))
    @Column(name = "glycemic_control")
    private List<String> glycemicControls;

    @ElementCollection
    @CollectionTable(name = "emr_hyperglycemic_episodes", joinColumns = @JoinColumn(name = "emr_id"))
    @Column(name = "hyperglycemic_episode")
    private List<String> hyperglycemicEpisodes;
}
