package com.pim.service;


import com.pim.dao.IProjectRepository;
import com.pim.dom.Project;
import com.pim.dom.QProject;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class ProjectService implements IProjectService{
    private IProjectRepository projectRepository;

    private Map<String,String> statusList = new HashMap<String,String>(){
        {
            put("NEW", "New");
            put("FIN", "Finished");
            put("PLA", "Planned");
            put("INP", "In progress");
        }
    };

    @Autowired
    public ProjectService(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project findById(Long id) {
        Optional<Project> projectOptional =  projectRepository.findById(id);
        return projectOptional.orElse(null);
    }

    @Override
    public Project findByProjectNumber(Integer projectnumber) {
        BooleanExpression booleanExpression = QProject.project.projectnumber.eq(projectnumber);
        Iterable<Project> projects = projectRepository.findAll(booleanExpression);
        long length = StreamSupport.stream(projects.spliterator(), false).count();
        if(length == 0){
            return null;
        }
        else{
            List<Project> projectList = convertIterableToList(projects);
            return projectList.get(0);
        }
    }

    @Override
    public void saveAll(List<Project> projects) {
        projectRepository.saveAll(projects);
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    public void delete(Project project) {
        projectRepository.delete(project);
    }

    @Override
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public Map<String, String> statusList() {
        return statusList;
    }

    public List<Project> convertIterableToList(Iterable<Project> iterable){
        List<Project> projects = new ArrayList<>();
        iterable.forEach(projects::add);
        return projects;
    }

//    @Override
//    public List<Project> countProject() {
//        BooleanExpression booleanExpression = QProject.project.projectnumber.goe(3117);
//        OrderSpecifier<Integer> orderSpecifier = QProject.project.projectnumber.asc();
//        Iterable<Project> projects = projectRepository.findAll(booleanExpression,orderSpecifier);
//        List<Project> myproject = new ArrayList<>();
//        projects.forEach(myproject::add);
//        return myproject;
//    }
}
