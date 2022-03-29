package com.foodtech.timetracking.data.repositories;

import com.foodtech.timetracking.data.entity.Employee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Long> {


}
