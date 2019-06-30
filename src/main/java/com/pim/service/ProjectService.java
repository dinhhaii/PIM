package com.pim.service;


import com.pim.dao.IProjectRepository;
import com.pim.dom.Project;
import com.pim.dom.QProject;
import com.pim.exception.ProjectNotExistsException;
import com.pim.exception.ProjectNumberAlreadyExistsException;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.locks.Lock;
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
    public ProjectService(IProjectRepository projectRepository, EntityManagerFactory entityManagerFactory) {
        if(entityManagerFactory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("Factory is not a hibernate factory");
        }
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
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
    @Transactional
    public Project findById(Long id) throws ProjectNotExistsException {
        Optional<Project> projectOptional =  projectRepository.findById(id);
        Project project = projectOptional.orElse(null);
        if(project == null){
            throw new ProjectNotExistsException("Project does not exist");
        }
        else{
            return project;
        }
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
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
//            Query query = session.createQuery("FROM Project WHERE id = :id");
//            query.setParameter("id", project.getId());
//
//            query.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
//            List<Project> projectList = query.getResultList();
//            if (projectList.size() == 0) {
//
//            } else {
//                transaction.commit();
//                Project _project = projectList.get(0);
//            }

            session.lock("Project", project, LockMode.OPTIMISTIC_FORCE_INCREMENT);
            session.update("Project", project);
//            session.lock(project, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            transaction.commit();
        }
        catch (RuntimeException e){
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
            throw e;
        }
        finally {
            session.close();
        }
    }

    @Override
    public void delete(Project project) {
        projectRepository.delete(project);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Query query = entityManager.createNativeQuery("delete from project_employee where project_id = :projectid");
        query.setParameter("projectid", id);
        query.executeUpdate();

//        projectRepository.deleteById(id);
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
