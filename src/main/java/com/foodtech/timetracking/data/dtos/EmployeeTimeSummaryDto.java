package com.foodtech.timetracking.data.dtos;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeTimeSummaryDto {

    private Long employeeId;
    private List<SummaryDto> summary;
}
