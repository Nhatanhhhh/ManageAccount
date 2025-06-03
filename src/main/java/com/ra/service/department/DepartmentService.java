package com.ra.service.department;

import com.ra.model.entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();

    Department getDepartmentById(Long id);

    Department saveDepartment(Department department);

    void toggleStatus(Long id);
}
