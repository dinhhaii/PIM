package com.pim.service;

import com.pim.dao.IGroupRepository;
import com.pim.dom.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService implements IGroupService {
    private IGroupRepository groupRepository;

    @Autowired
    public GroupService(IGroupRepository groupRepository) {
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
