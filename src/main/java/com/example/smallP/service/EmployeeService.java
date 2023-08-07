package com.example.smallP.service;

import com.example.smallP.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();

    Employee findById (int theId);

    Employee save (Employee theEmployee);

    void deleteById(int theId);
}
