package com.pim.dom;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "[group]")
public class Group implements IBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "version", length = 10, nullable = false)
    private Integer version;

    @OneToOne
    @JoinColumn(name = "group_leader_id", nullable = false)
    private Employee group_leader;


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

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", version=" + version +
                ", group_leader=" + group_leader +
                '}';
    }
}
