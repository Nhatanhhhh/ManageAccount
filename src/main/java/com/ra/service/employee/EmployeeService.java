package com.ra.service.employee;

import com.ra.model.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();

    Employee findById(Long id);

    Employee saveEmployee(Employee employee);

    void toggleStatus(Long id);

    List<Employee> findByDepartmentId(Long departmentId);

    void deleteById(Long id);
}
