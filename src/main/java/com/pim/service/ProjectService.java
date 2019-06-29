package com.pim.service;


import com.pim.dao.IProjectRepository;
import com.pim.dom.Project;
import com.pim.dom.QProject;
import com.pim.exception.ProjectNumberAlreadyExistsException;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class ProjectService implements IProjectService{
    @PersistenceContext
    private EntityManager entityManager;
    private SessionFactory sessionFactory;

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
        QProject project = QProject.project;
        JPQLQuery query = new JPAQuery(entityManager);
        List<Project> projects = query.from(project).fetch();
        return projects;
//        return projectRepository.findAll();
    }

    @Override
    public Project findById(Long id) {
        Optional<Project> projectOptional =  projectRepository.findById(id);
        return projectOptional.orElse(null);
    }

    @Override
    public Project findByProjectNumber(Integer projectnumber) throws ProjectNumberAlreadyExistsException {
        BooleanExpression booleanExpression = QProject.project.projectnumber.eq(projectnumber);
        Iterable<Project> projects = projectRepository.findAll(booleanExpression);

        long length = StreamSupport.stream(projects.spliterator(), false).count();
        if(length == 0){
            return null;
        }
        else{
//            List<Project> projectList = convertIterableToList(projects);
//            return projectList.get(0);
            throw new ProjectNumberAlreadyExistsException("The project number already existed.");
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
        QProject project = QProject.project;
        Query query = entityManager.createQuery("delete from project where id = :projectid");
        query.setParameter("projectid",id);
        query.executeUpdate();
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
