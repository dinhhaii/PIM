package com.pim.service;

import com.google.common.collect.Ordering;
import com.pim.dao.IProjectRepository;
import com.pim.dom.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManagerFactory;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class ProjectServiceTest {

//    @Autowired
//    private ProjectService projectService;


    @Test
    public void sortedProjectList(){
//        List<Project> projectList = projectService.findAll();
//        List<Integer> projectNumberList = projectList.stream().map(Project::getProjectnumber).collect(Collectors.toList());
//        boolean sorted = Ordering.natural().isOrdered(projectNumberList);
//        assertEquals(sorted, true);

    }
}