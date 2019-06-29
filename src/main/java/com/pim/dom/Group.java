package com.pim.dom;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "[group]")
public class Group implements IBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    @Column(name = "version", length = 10)
    private Integer version;

    @ManyToOne
    @NotFound(action= NotFoundAction.IGNORE)
    @JoinColumn(name = "group_leader_id")
    private Employee group_leader;

    @OneToMany(mappedBy = "group", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    public Group(){}

    public Group(Integer version, Employee group_leader) {
        this.version = version;
        this.group_leader = group_leader;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Employee getGroup_leader() {
        return group_leader;
    }

    public void setGroup_leader(Employee group_leader) {
        this.group_leader = group_leader;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", version=" + version +
                ", group_leader=" + group_leader +
                '}';
    }
}
