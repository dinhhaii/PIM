package com.pim.service;


import com.pim.dao.IProjectRepository;
import com.pim.dom.Employee;
import com.pim.dom.Project;
import com.pim.dom.QProject;
import com.pim.exception.ConcurrentUpdateProjectException;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.stream.StreamSupport;

@Service
public class ProjectService implements IProjectService{
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private EmployeeService employeeService;

    private IProjectRepository projectRepository;
    private SessionFactory sessionFactory;

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
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    public void edit(Project project) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            Project _project = session.load(Project.class, project.getId());

            _project.setName(project.getName());
            _project.setProjectnumber(project.getProjectnumber());
            _project.setGroup(project.getGroup());
            _project.setCustomer(project.getCustomer());
            _project.setStatus(project.getStatus());
            _project.setStartDate(project.getStartDate());
            _project.setEndDate(project.getEndDate());
            _project.setEmployees(project.getEmployees());

            Project oldProject = findById(project.getId());
            if(oldProject.getVersion() == project.getVersion()){
                throw new ConcurrentUpdateProjectException("Concurrent update project exception");
            }
            transaction.commit();

        }
        catch (RuntimeException e){
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
            throw e;
        }
        catch(ProjectNotExistsException e){
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
        }
        catch (ConcurrentUpdateProjectException e){
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
        }
        finally {
            session.close();
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaDelete<Project> criteriaDelete = criteriaBuilder.createCriteriaDelete(Project.class);
            Root projectRoot = criteriaDelete.from(Project.class);
            criteriaDelete.where(criteriaBuilder.equal(projectRoot.get("id"),id));
            Query query = session.createQuery(criteriaDelete);
            query.executeUpdate();
            transaction.commit();
        }
        catch(RuntimeException e){
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
    public List<Project> search(String keyword, String status) throws ProjectNotExistsException {
        StringBuilder searchStringBuilder = new StringBuilder("");
        searchStringBuilder.append("%");
        searchStringBuilder.append(keyword);
        searchStringBuilder.append("%");
        String search = searchStringBuilder.toString();

        String searchStatusAndKeyword = "select * from project where (project_number like :search or customer like :search or name like :search) and status = :status order by project_number asc";
        String searchKeyword = "select project.* from project where project_number like :search or customer like :search or name like :search order by project_number asc";
        String searchStatus = "select project.* from project where status = :status order by project_number asc";

        List<Project> searchList = new ArrayList<>();

        if (status != null && !status.equals("")){
            if(keyword == null){
                Query query = entityManager.createNativeQuery(searchStatus, Project.class);
                query.setParameter("status", status);
                searchList = query.getResultList();
            }else{
                Query query = entityManager.createNativeQuery(searchStatusAndKeyword, Project.class);
                query.setParameter("search", search);
                query.setParameter("status", status);
                searchList = query.getResultList();
            }
        }
        else{
            if(keyword != null){
                Query query = entityManager.createNativeQuery(searchKeyword, Project.class);
                query.setParameter("search", search);
                searchList = query.getResultList();
            }
        }

        if(searchList.size() == 0){
            throw new ProjectNotExistsException("Projects is not available");
        }
        else{
            return searchList;
        }
    }

    @Override
    public Map<String, String> statusList() {
        return statusList;
    }

    @Override
    public String convertEmployeeSetToString(Set<Employee> employeeSet){
        List<Employee> memberList = new ArrayList<>(employeeSet);

        StringBuilder members = new StringBuilder("");
        for (int i = 0; i < memberList.size(); i++) {
            Employee employee = memberList.get(i);
            String visa = employee.getVisa();
            String firstName = employee.getFirstName();
            String lastName = employee.getLastName();

            members.append(visa);
            members.append(":");
            members.append(firstName);
            members.append(lastName);
            members.append(",");
        }

        String memberInput = members.toString();
        int length = memberInput.length();
        if(length > 0){
            return memberInput.substring(0, length - 1);
        }else{
            return "";
        }
    }

    @Override
    public Set<Employee> convertStringToEmployeeSet(String member){
        Set<Employee> employeeSet = new HashSet<>();
        String[] infoMembers = member.split(",");
        for(int i = 0;i<infoMembers.length;i++){
            String infoMember = infoMembers[i];
            String visa = infoMember.substring(0,infoMember.indexOf(':'));
            Employee employee = employeeService.findByVisa(visa);
            employeeSet.add(employee);
        }
        return employeeSet;
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
