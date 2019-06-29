package com.pim.controller;


import com.pim.dom.Employee;
import com.pim.dom.Group;
import com.pim.dom.Project;
import com.pim.service.EmployeeService;
import com.pim.service.GroupService;
import com.pim.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ApplicationController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = {"/newproject"}, method = RequestMethod.GET)
    public String index(Model model){
        List<Group> groups = groupService.findAll();
        List<Employee> employees = employeeService.findAll();
        Map<String,String> statusList = projectService.statusList();

        model.addAttribute("groups", groups);
        model.addAttribute("statusList", statusList);
        model.addAttribute("employees", employees);
        return "newproject";
    }

    @RequestMapping(value = {"/editproject/{id}"}, method = RequestMethod.GET)
    public String editProject(Model model, @PathVariable Long id){
        Project project = projectService.findById(id);
        Map<String,String> statusList = projectService.statusList();
        List<Group> groups = groupService.findAll();

        model.addAttribute("project", project);
        model.addAttribute("groups", groups);
        model.addAttribute("statusList", statusList);
        return "editproject";
    }

    @RequestMapping(value = {"/editproject/{id}"}, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String editProject(@PathVariable Long id,
                              @RequestParam(name = "projectnumber") Integer projectnumber,
                              @RequestParam(name = "name") String name,
                              @RequestParam(name = "customer") String customer,
                              @RequestParam(name = "group") Long group_id,
                              @RequestParam(name = "member") String member,
                              @RequestParam(name = "status") String status,
                              @RequestParam(name = "startDate") Date startDate,
                              @RequestParam(name = "endDate") Date endDate){
        //============================
        Integer version = 0;
        Set<Employee> project_employee = new HashSet<>();


        Group group = groupService.findById(group_id);
        Project project = new Project(id,version,projectnumber,name,customer,status,startDate,endDate,group,project_employee);
        projectService.save(project);
        return "redirect:/projectlist";
    }

    @RequestMapping(value = {"/","/index","projectlist"}, method = RequestMethod.GET)
    public String projectlist(Model model){
        List<Project> projects = projectService.findAll();
        Map<String,String> statusList = projectService.statusList();

        model.addAttribute("projects", projects);
        model.addAttribute("statusList", statusList);
        return "projectlist";
    }

    @RequestMapping(value = "/createproject", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public String createProject(@RequestParam(name = "projectnumber") Integer projectnumber,
                                @RequestParam(name = "name") String name,
                                @RequestParam(name = "customer") String customer,
                                @RequestParam(name = "group") Long group_id,
                                @RequestParam(name = "member") String member,
                                @RequestParam(name = "status") String status,
                                @RequestParam(name = "startDate") Date startDate,
                                @RequestParam(name = "endDate") Date endDate){
        //============================
        Integer version = 1;
        Set<Employee> project_employee = new HashSet<>();

        String[] infoMembers = member.split(",");
        for(int i = 0;i<infoMembers.length;i++){
            String infoMember = infoMembers[i];
            String visa = infoMember.substring(0,infoMember.indexOf(':'));
            Employee employee = employeeService.findByVisa(visa);
            project_employee.add(employee);
        }

        Group group = groupService.findById(group_id);
        Project project = new Project(version, projectnumber,name,customer,status,startDate,endDate,group, project_employee);
        System.out.println(project);
        projectService.save(project);
        return "redirect:/projectlist";
    }

    @RequestMapping(value = "/deleteproject", method = RequestMethod.POST)
    public String deleteProject(@RequestParam(name = "id") Long id){
        projectService.deleteById(id);
        return "redirect:/projectlist";
    }

    @RequestMapping(value = "/createproject/is-available-projectnumber", method = RequestMethod.GET)
    public @ResponseBody boolean deleteProject(@RequestParam(name = "projectnumber") Integer projectnumber){
        Project project = projectService.findByProjectNumber(projectnumber);
        if(project == null) {
            return true;
        }else{
            return false;
        }
    }
}
