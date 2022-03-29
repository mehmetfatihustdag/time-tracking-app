package com.foodtech.timetracking.data.dtos;

import com.foodtech.timetracking.data.enums.TimeTrackingEnum;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeTrackingResponseDto {

    private Long employeeId;
    private LocalDateTime time;
    private TimeTrackingEnum timeTrackingEnum;

}
