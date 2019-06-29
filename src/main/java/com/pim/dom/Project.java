package com.pim.dom;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "version", length = 10, nullable = false)
    private Integer version;
    @Column(name = "project_number", length = 4, nullable = false)
    private Integer projectnumber;
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name = "customer", length = 50, nullable = false)
    private String customer;
    @Column(name = "status", length = 3, nullable = false)
    private String status;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "project_employee",
        joinColumns = {@JoinColumn(name = "project_id")},
        inverseJoinColumns = {@JoinColumn(name = "employee_id")})
    private Set<Employee> employees = new HashSet<>();

    public Project() {
    }

    public Project(Long id,Integer version, Integer projectnumber, String name, String customer, String status, Date startDate, Date endDate, Group group, Set<Employee> employees) {
        this.id = id;
        this.version = version;
        this.projectnumber = projectnumber;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.group = group;
        this.employees = employees;
    }

    public Project(Integer version, Integer projectnumber, String name, String customer, String status, Date startDate, Date endDate, Group group, Set<Employee> employees) {
        this.version = version;
        this.projectnumber = projectnumber;
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.group = group;
        this.employees = employees;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProjectnumber() {
        return projectnumber;
    }

    public void setProjectnumber(Integer projectnumber) {
        this.projectnumber = projectnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", version=" + version +
                ", projectnumber=" + projectnumber +
                ", name='" + name + '\'' +
                ", customer='" + customer + '\'' +
                ", status='" + status + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", group=" + group +
                ", employees=" + employees +
                '}';
    }
}
