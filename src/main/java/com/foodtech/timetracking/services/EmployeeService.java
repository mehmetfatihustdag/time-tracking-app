package com.foodtech.timetracking.services;

import com.foodtech.timetracking.data.entity.Employee;


/**
 * Employee service interface
 */
public interface EmployeeService {

    /**
     *
     * @param employeeId to retrieve employee
     * @return return employee object
     */
    Employee getEmployee(Long employeeId);

}
