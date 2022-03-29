package com.foodtech.timetracking.data.dtos;

import com.foodtech.timetracking.data.enums.TimeTrackingEnum;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeTrackRequestDto {

   private LocalDateTime time;
   private TimeTrackingEnum timeTrackingEnum;

}
