package com.foodtech.timetracking.services.factory;

import static com.foodtech.timetracking.data.enums.TimeTrackingEnum.PUNCH;

import static java.util.Comparator.comparing;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.foodtech.timetracking.data.entity.Employee;
import com.foodtech.timetracking.data.entity.TimeTrack;
import com.foodtech.timetracking.data.repositories.TimeTrackingRepository;
import com.foodtech.timetracking.exceptions.CheckInException;
import com.foodtech.timetracking.exceptions.CheckOutException;
import com.foodtech.timetracking.services.EmployeeService;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * PunchTime Service : Holding the punchIn- punchOut related operations
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PunchTimeService implements TimeTrackingHandler {

    private final TimeTrackingRepository timeTrackingRepository;
    private final EmployeeService employeeService;

    @Override
    public String getType() {
        return PUNCH.name();
    }

    /**
     * @param employeeId  employee to punchIn
     * @param checkInDate requested time to punchIn
     */
    @Override
    public void checkIn(Long employeeId, LocalDateTime checkInDate) {
        log.info(
                "Punch-in operation started for the employee with an id {}, for the punchIn date : {}",
                employeeId, checkInDate);
        Employee employee = employeeService.getEmployee(employeeId);

        TimeTrack latestTimeTrack = getLatestTimeTrack(employee);

        if (nonNull(latestTimeTrack) && isNull(latestTimeTrack.getPunchOut())) {
            throw new CheckInException(
                    "There is already open punchIn for the user. PunchIn needs to be closed");

        }

        createCheckIn(checkInDate, employee);
    }

    private void createCheckIn(LocalDateTime checkInDate, Employee employee) {
        TimeTrack timeTrack = new TimeTrack();
        timeTrack.setPunchIn(checkInDate);
        timeTrack.setTrackDate(checkInDate.toLocalDate());
        timeTrack.setEmployee(employee);

        timeTrackingRepository.save(timeTrack);
    }

    /**
     * @param employeeId   employee to punchOut
     * @param checkOutDate requested time to punchOut
     */
    @Override
    public void checkOut(Long employeeId, LocalDateTime checkOutDate) {
        log.info(
                "Punch-out operation started for the employee with an id {}, for the punchIn date : {}",
                employeeId, checkOutDate);

        Employee employee = employeeService.getEmployee(employeeId);

        TimeTrack timeTrack = getLatestTimeTrack(employee);

        if (isNull(timeTrack) || nonNull(timeTrack.getPunchOut())) {
            throw new CheckOutException(
                    "There is no open punchIn to punchOut");
        }

        if (checkOutDate.isBefore(timeTrack.getPunchIn()) || checkOutDate
                                                                     .equals(timeTrack
                                                                                     .getPunchIn())) {
            throw new CheckOutException(
                    "punch-out time can be earlier than punchin time");
        }

        timeTrack.setPunchOut(checkOutDate);

        timeTrackingRepository.save(timeTrack);
    }


    private TimeTrack getLatestTimeTrack(Employee employee) {
        return employee.getTimeTrackSet().stream()
                       .max(comparing(
                               TimeTrack::getPunchIn))
                       .orElse(null);
    }
}
