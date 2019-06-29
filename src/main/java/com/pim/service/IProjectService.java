package com.pim.service;

import com.pim.dom.Project;
import com.pim.exception.ProjectNumberAlreadyExistsException;

import java.util.List;
import java.util.Map;

public interface IProjectService extends IService<Project> {
    List<Project> findAll();
    Project findById(Long id);
    Project findByProjectNumber(Integer projectnumber) throws ProjectNumberAlreadyExistsException;
    void saveAll(List<Project> projects);
    void save(Project project);
    void delete(Project project);
    void deleteById(Long id);
    Map<String,String> statusList();
}
