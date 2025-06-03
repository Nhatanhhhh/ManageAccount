package com.ra.controller;

import com.ra.model.dto.DataError;
import com.ra.model.entity.Department;
import com.ra.service.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<List<Department>> getAll() {
        List<Department> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        if (department == null) {
            return new ResponseEntity<>(new DataError("Department not found!", 404), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(department, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createDepartment(@RequestBody Department department) {
        Department newDepartment = departmentService.saveDepartment(department);
        return new ResponseEntity<>(newDepartment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody Department department) {
        Department existing = departmentService.getDepartmentById(id);
        if (existing == null) {
            return new ResponseEntity<>(new DataError("Department not found!", 404), HttpStatus.NOT_FOUND);
        }
        existing.setName(department.getName());
        existing.setStatus(department.getStatus());
        Department updatedDepartment = departmentService.saveDepartment(existing);
        return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<?> toggleDepartmentStatus(@PathVariable Long id) {
        Department department = departmentService.getDepartmentById(id);
        if (department == null) {
            return new ResponseEntity<>(new DataError("Department not found!", 404), HttpStatus.NOT_FOUND);
        }
        departmentService.toggleStatus(id);
        return new ResponseEntity<>(new DataError("Department status toggled successfully!", 200), HttpStatus.OK);
    }
}
