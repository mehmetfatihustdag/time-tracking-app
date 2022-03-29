package com.foodtech.timetracking.data.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class SummaryDto {

    private LocalDate date;
    private LocalDateTime punchIn;
    private LocalDateTime punchOut;
}
