package com.foodtech.timetracking.services;

import com.foodtech.timetracking.data.dtos.EmployeeTimeSummaryDto;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


/**
 * Report service interface
 */
public interface ReportService {

    /**
     * Report method for the specific Employee for the given interval
     */
    EmployeeTimeSummaryDto getReportForEmployee(Long employeeId, LocalDate startDate, LocalDate endDate);

    /**
     * Report method for the list of employees for the given date
     */
    List<EmployeeTimeSummaryDto> getReportForEmployees(LocalDate searchDate);

}
