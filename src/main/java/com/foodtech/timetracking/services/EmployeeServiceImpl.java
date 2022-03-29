package com.foodtech.timetracking.services;

import static java.lang.String.format;

import com.foodtech.timetracking.data.entity.Employee;
import com.foodtech.timetracking.data.repositories.EmployeeRepository;
import com.foodtech.timetracking.exceptions.EntityNotFoundException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(
                () -> new EntityNotFoundException(
                        format("There is no employee with the id : %s",
                                employeeId)));
    }

}
