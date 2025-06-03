package com.ra.controller;

import com.ra.model.dto.DataError;
import com.ra.model.entity.Employee;
import com.ra.service.department.DepartmentService;
import com.ra.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    private ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            return new ResponseEntity<>(new DataError("Employee not found!", 404), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        if (employee.getDepartment() != null && employee.getDepartment().getId() != null) {
            if (departmentService.getDepartmentById(employee.getDepartment().getId()) == null) {
                return new ResponseEntity<>(new DataError("Department not found!", 404), HttpStatus.NOT_FOUND);
            }
        }
        Employee newEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee existing = employeeService.findById(id);
        if (existing == null) {
            return new ResponseEntity<>(new DataError("Employee not found!", 404), HttpStatus.NOT_FOUND);
        }
        if (employee.getDepartment() != null && employee.getDepartment().getId() != null) {
            if (departmentService.getDepartmentById(employee.getDepartment().getId()) == null) {
                return new ResponseEntity<>(new DataError("Department not found!", 404), HttpStatus.NOT_FOUND);
            }
        }
        employee.setId(id);
        Employee updatedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<?> toggleEmployeeStatus(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            return new ResponseEntity<>(new DataError("Employee not found!", 404), HttpStatus.NOT_FOUND);
        }
        employeeService.toggleStatus(id);
        return new ResponseEntity<>(new DataError("Employee status toggled successfully!", 200), HttpStatus.OK);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<?> getEmployeesByDepartment(@PathVariable Long departmentId) {
        if (departmentService.getDepartmentById(departmentId) == null) {
            return new ResponseEntity<>(new DataError("Department not found!", 404), HttpStatus.NOT_FOUND);
        }
        List<Employee> employees = employeeService.findByDepartmentId(departmentId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PatchMapping("/{id}/remove-department")
    public ResponseEntity<?> removeEmployeeFromDepartment(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            return new ResponseEntity<>(new DataError("Employee not found!", 404), HttpStatus.NOT_FOUND);
        }
        employee.setDepartment(null);
        employeeService.saveEmployee(employee);
        return new ResponseEntity<>(new DataError("Employee removed from department successfully!", 200), HttpStatus.OK);
    }
}
