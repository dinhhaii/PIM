package com.pim.service;

import com.pim.dao.IEmployeeRepository;
import com.pim.dom.Employee;
import com.pim.dom.QEmployee;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class EmployeeService implements IEmployeeService{
    private IEmployeeRepository employeeRepository;
    private SessionFactory sessionFactory;

    @Autowired
    public EmployeeService(IEmployeeRepository employeeRepository, EntityManagerFactory entityManagerFactory) {
        if(entityManagerFactory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("Factory is not a hibernate factory");
        }
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee findById(Long id) {
        Optional<Employee> employeeOptional =  employeeRepository.findById(id);
        return employeeOptional.orElse(null);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findByVisa(String visa) {
        BooleanExpression booleanExpression = QEmployee.employee.visa.eq(visa);
        Iterable<Employee> employees = employeeRepository.findAll(booleanExpression);
        long length = StreamSupport.stream(employees.spliterator(), false).count();
        if(length == 0){
            return null;
        }
        else{
            List<Employee> employeeList = convertIterableToList(employees);
            return employeeList.get(0);
        }
    }

    @Override
    public List<Employee> convertIterableToList(Iterable<Employee> iterable) {
        List<Employee> employees = new ArrayList<>();
        iterable.forEach(employees::add);
        return employees;
    }
}
