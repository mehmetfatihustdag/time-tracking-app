package com.foodtech.timetracking.services.factory;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * TimeTrackingHandler interface
 */
@Service
public interface TimeTrackingHandler {

    /**
     *
     * @return Type of the handler
     */
    String getType();

    /**
     *
     * @param employeeId employee to checkIn
     * @param checkInDate requested time to checkIn
     */
    void checkIn(Long employeeId, LocalDateTime checkInDate);

    /**
     *
     * @param employeeId employee to checkOut
     * @param checkOutDate requested time to checkOut
     */
    void checkOut(Long employeeId,LocalDateTime checkOutDate);

}
