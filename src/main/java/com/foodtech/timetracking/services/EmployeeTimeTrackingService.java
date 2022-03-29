package com.foodtech.timetracking.services;

import com.foodtech.timetracking.data.dtos.TimeTrackRequestDto;
import com.foodtech.timetracking.data.dtos.EmployeeTimeSummaryDto;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeTimeTrackingService {

    /**
     *
     * @param employeeId
     * @param timeTrackRequestDto having time for the checkIn, and enum type for the checkIn type (PUNCH)
     */
    void checkIn(Long employeeId, TimeTrackRequestDto timeTrackRequestDto);

    /**
     *
     * @param employeeId
     * @param timeTrackRequestDto having time for the checkOut, and enum type for the checkOut type (PUNCH)
     */
    void checkOut(Long employeeId, TimeTrackRequestDto timeTrackRequestDto);

    /**
     *
      * @param employeeId
     * @param startDate
     * @param endDate
     * @return employee time summary info for the given time interval
     */
    EmployeeTimeSummaryDto reportEmployeeTime(Long employeeId, LocalDate startDate, LocalDate endDate);

    /**
     *
     * @param searchDate
     * @return employees time summary info for the given date
     */
    List<EmployeeTimeSummaryDto> reportEmployeesTime(LocalDate searchDate);

}
