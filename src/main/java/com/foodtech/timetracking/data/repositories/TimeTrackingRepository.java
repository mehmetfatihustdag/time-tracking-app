package com.foodtech.timetracking.data.repositories;

import com.foodtech.timetracking.data.entity.Employee;
import com.foodtech.timetracking.data.entity.TimeTrack;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeTrackingRepository extends JpaRepository<TimeTrack,Long> {

    List<TimeTrack> findTimeTrackByEmployeeAndTrackDateBetweenOrderByTrackDateAsc(Employee employee,LocalDate startDate, LocalDate endDate);

    List<TimeTrack> findTimeTrackByTrackDate(LocalDate searchDate);

}
