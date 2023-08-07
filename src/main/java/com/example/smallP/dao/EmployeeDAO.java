package com.example.smallP.dao;

import com.example.smallP.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> findAll();

    Employee findById (int theId);

    Employee save (Employee theEmployee);

    void deleteById(int theId);
}
