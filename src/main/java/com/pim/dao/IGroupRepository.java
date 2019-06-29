package com.pim.dao;

import com.pim.dom.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupRepository extends JpaRepository<Group, Long>, QuerydslPredicateExecutor<Group> {
}
