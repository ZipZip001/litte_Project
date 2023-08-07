package com.example.smallP.dao;

import com.example.smallP.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> findAll();

}
