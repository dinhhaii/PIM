package com.pim.service;


import com.pim.dom.Employee;

import java.util.List;

public interface IEmployeeService extends IService<Employee> {
    Employee findById(Long id);
    List<Employee> findAll();
    Employee findByVisa(String visa);
}
