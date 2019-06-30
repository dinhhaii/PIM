package com.pim.service;

import com.pim.dao.IGroupRepository;
import com.pim.dom.Group;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService implements IGroupService {
    private IGroupRepository groupRepository;
    private SessionFactory sessionFactory;

    @Autowired
    public GroupService(IGroupRepository groupRepository, EntityManagerFactory entityManagerFactory) {
        if(entityManagerFactory.unwrap(SessionFactory.class) == null){
            throw new NullPointerException("Factory is not a hibernate factory");
        }
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> convertIterableToList(Iterable<Group> iterable) {
        List<Group> groups = new ArrayList<>();
        iterable.forEach(groups::add);
        return groups;
    }

    @Override
    public Group findById(Long id) {
        Optional<Group> groupOptional =  groupRepository.findById(id);
        return groupOptional.orElse(null);
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }
}
