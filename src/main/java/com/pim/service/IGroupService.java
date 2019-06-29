package com.pim.service;

import com.pim.dom.Group;

import java.util.List;

public interface IGroupService extends IService<Group> {
    Group findById(Long id);
    List<Group> findAll();
}
