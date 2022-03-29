package com.foodtech.timetracking.resources;

import com.foodtech.timetracking.data.dtos.TimeTrackingResponseDto;
import com.foodtech.timetracking.data.dtos.TimeTrackRequestDto;
import com.foodtech.timetracking.data.dtos.EmployeeTimeSummaryDto;
import com.foodtech.timetracking.services.EmployeeTimeTrackingService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/time-trackings")
@RequiredArgsConstructor
public class TimeTrackingResource {

    private final EmployeeTimeTrackingService employeeTimeTrackingService;

    @PostMapping("/check-in/employees/{id}")
    @Operation(summary = "CheckIn Operation for the user")
    @Parameter(name = "id", description = "id of the user")
    @Parameter(name = "timeTrackRequestDto", description = "timeTrackingRequestDto holding the requested time , and checking type(PUNCH)")
    public ResponseEntity<TimeTrackingResponseDto> checkIn(
            @PathVariable Long id,
            @RequestBody TimeTrackRequestDto timeTrackRequestDto) {
        employeeTimeTrackingService.checkIn(id, timeTrackRequestDto);
        TimeTrackingResponseDto timeTrackingResponseDto =
                getTimeTrackingResponseDto(id, timeTrackRequestDto);

        return ResponseEntity.created(URI.create("/check-in/employees"))
                       .body(timeTrackingResponseDto);
    }


    @PostMapping("/check-out/employees/{id}")
    @Operation(summary = "CheckOut Operation for the user")
    @Parameter(name = "id", description = "id of the user")
    @Parameter(name = "timeTrackRequestDto", description = "timeTrackingRequestDto holding the requested time , and checking type(PUNCH)")
    public ResponseEntity<TimeTrackingResponseDto> checkOut(
            @PathVariable Long id,
            @RequestBody TimeTrackRequestDto timeTrackRequestDto) {

        employeeTimeTrackingService.checkOut(id, timeTrackRequestDto);
        TimeTrackingResponseDto timeTrackingResponseDto =
                getTimeTrackingResponseDto(id, timeTrackRequestDto);

        return ResponseEntity.created(URI.create("/check-in/employees"))
                       .body(timeTrackingResponseDto);

    }

    private TimeTrackingResponseDto getTimeTrackingResponseDto(Long id,
            TimeTrackRequestDto timeTrackRequestDto) {
        TimeTrackingResponseDto timeTrackingResponseDto =
                new TimeTrackingResponseDto();
        timeTrackingResponseDto.setTime(timeTrackRequestDto.getTime());
        timeTrackingResponseDto
                .setTimeTrackingEnum(timeTrackRequestDto.getTimeTrackingEnum());
        timeTrackingResponseDto.setEmployeeId(id);
        return timeTrackingResponseDto;
    }

    @GetMapping("/employees/{id}/{startDate}/{endDate}")
    @Operation(summary = "Time tracking Report for the specific employee")
    @Parameter(name = "startDate", description = "start time of the report")
    @Parameter(name = "endDate", description = "end time of the report")
    public EmployeeTimeSummaryDto reportTime(@PathVariable Long id,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return employeeTimeTrackingService
                       .reportEmployeeTime(id, startDate, endDate);
    }

    @GetMapping("/employees")
    @Operation(summary = "Time tracking Report for the employees")
    @Parameter(name = "searchDate", description = "date of the report")
    public List<EmployeeTimeSummaryDto> reportTime(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate searchDate) {

        return employeeTimeTrackingService
                       .reportEmployeesTime(searchDate);
    }

}
