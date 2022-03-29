package com.foodtech.timetracking.services;

import com.foodtech.timetracking.data.dtos.TimeTrackRequestDto;
import com.foodtech.timetracking.data.dtos.EmployeeTimeSummaryDto;
import com.foodtech.timetracking.services.factory.TimeServiceFactory;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class EmployeeTimeTrackingServiceImpl
        implements EmployeeTimeTrackingService {

    private final TimeServiceFactory timeServiceFactory;
    private final ReportService reportService;

    @Override
    public void checkIn(Long employeeId,
            TimeTrackRequestDto timeTrackRequestDto) {
        log.info("CheckIn operation started for the employee with an id : {}",
                employeeId);
        timeServiceFactory
                .getHandler(timeTrackRequestDto.getTimeTrackingEnum())
                .checkIn(employeeId, timeTrackRequestDto.getTime());
    }

    @Override
    public void checkOut(Long employeeId,
            TimeTrackRequestDto timeTrackRequestDto) {
        log.info("Checkout operation started for the employee with an id : {}",
                employeeId);

        timeServiceFactory
                .getHandler(timeTrackRequestDto.getTimeTrackingEnum())
                .checkOut(employeeId, timeTrackRequestDto.getTime());
    }

    @Override
    public EmployeeTimeSummaryDto reportEmployeeTime(Long employeeId,
            LocalDate startDate, LocalDate endDate) {
        log.info(
                "Report is going to retrieved for the employee with an id : {}, for the start date : {}, and end date : {}",
                employeeId, startDate, endDate);

        return reportService
                       .getReportForEmployee(employeeId, startDate, endDate);
    }

    @Override
    public List<EmployeeTimeSummaryDto> reportEmployeesTime(
            LocalDate searchDate) {
        log.info(
                "Report is going to retrieved for the employees for an given time : {}",
               searchDate);
        return reportService.getReportForEmployees(searchDate);
    }
}
