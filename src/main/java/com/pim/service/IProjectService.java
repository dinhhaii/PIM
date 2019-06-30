package com.pim.service;

import com.pim.dom.Employee;
import com.pim.dom.Project;
import com.pim.exception.ProjectNotExistsException;
import com.pim.exception.ProjectNumberAlreadyExistsException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IProjectService extends IService<Project> {
    List<Project> findAll();
    Project findById(Long id) throws ProjectNotExistsException;
    Project findByProjectNumber(Integer projectnumber) throws ProjectNumberAlreadyExistsException;
    void save(Project project);
    void edit(Project project);
    void deleteById(Long id);

    List<Project> search(String keyword, String status) throws ProjectNotExistsException;

    Map<String,String> statusList();
    String convertEmployeeSetToString(Set<Employee> employeeSet);
    Set<Employee> convertStringToEmployeeSet(String memberInput);
}
