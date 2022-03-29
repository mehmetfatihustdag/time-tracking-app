package com.foodtech.timetracking.services;


import static java.util.Collections.singletonList;

import com.foodtech.timetracking.data.dtos.EmployeeTimeSummaryDto;
import com.foodtech.timetracking.data.dtos.SummaryDto;
import com.foodtech.timetracking.data.entity.Employee;
import com.foodtech.timetracking.data.entity.TimeTrack;
import com.foodtech.timetracking.data.repositories.TimeTrackingRepository;
import com.foodtech.timetracking.exceptions.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final EmployeeService employeeService;
    private final TimeTrackingRepository timeTrackingRepository;

    @Override
    public EmployeeTimeSummaryDto getReportForEmployee(Long employeeId,
            LocalDate startDate, LocalDate endDate) {

        log.info(
                "Getting report for the employee with an id : {}, for the time interval {} , {}",
                employeeId, startDate, endDate);

        Employee employee = employeeService.getEmployee(employeeId);

        List<TimeTrack> timeTracks = timeTrackingRepository
                                             .findTimeTrackByEmployeeAndTrackDateBetweenOrderByTrackDateAsc(
                                                     employee, startDate,
                                                     endDate);

        if (CollectionUtils.isEmpty(timeTracks)) {
            throw new EntityNotFoundException(
                    "No time track found for the employee");
        }

        return mapEmployeeTimeSummaryDto(employeeId, timeTracks);
    }

    private EmployeeTimeSummaryDto mapEmployeeTimeSummaryDto(Long employeeId,
            List<TimeTrack> timeTracks) {
        EmployeeTimeSummaryDto employeeTimeSummaryDto =
                new EmployeeTimeSummaryDto();
        employeeTimeSummaryDto.setEmployeeId(employeeId);
        List<SummaryDto> summaryDtos = new ArrayList<>();
        timeTracks.forEach(timeTrack -> {
            SummaryDto summaryDto = new SummaryDto();
            summaryDto.setDate(timeTrack.getTrackDate());
            summaryDto.setPunchIn(timeTrack.getPunchIn());
            summaryDto.setPunchOut(timeTrack.getPunchOut());
            summaryDtos.add(summaryDto);
        });

        employeeTimeSummaryDto.setSummary(summaryDtos);
        return employeeTimeSummaryDto;
    }


    @Override
    public List<EmployeeTimeSummaryDto> getReportForEmployees(
            LocalDate searchDate) {

        log.info(
                "Getting reports for the employees for the date : {}",
               searchDate);

        List<EmployeeTimeSummaryDto> employeeTimeSummaryDtos =
                new ArrayList<>();

        List<TimeTrack> timeTracks =
                timeTrackingRepository.findTimeTrackByTrackDate(searchDate);
        timeTracks.forEach(timeTrack -> {
            Employee employee = timeTrack.getEmployee();
            if (!employee.isManager()) {

                EmployeeTimeSummaryDto employeeTimeSummaryDto =
                        new EmployeeTimeSummaryDto();
                employeeTimeSummaryDto.setEmployeeId(employee.getId());

                SummaryDto summaryDto = new SummaryDto();
                summaryDto.setDate(timeTrack.getTrackDate());
                summaryDto.setPunchIn(timeTrack.getPunchIn());
                summaryDto.setPunchOut(timeTrack.getPunchOut());

                employeeTimeSummaryDto.setSummary(
                        singletonList(summaryDto));

                employeeTimeSummaryDtos.add(employeeTimeSummaryDto);
            }
        });

        return employeeTimeSummaryDtos;
    }

}

