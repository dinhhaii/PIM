package com.pim.controller;


import com.pim.dom.Employee;
import com.pim.dom.Group;
import com.pim.dom.Project;
import com.pim.exception.ProjectNotExistsException;
import com.pim.exception.ProjectNumberAlreadyExistsException;
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

    @RequestMapping(value = {"/","/index","projectlist"}, method = RequestMethod.GET)
    public String projectlist(Model model, @RequestParam(required = false) String keyword, @RequestParam(required = false) String status){
        List<Project> projects = projectService.findAll();
        Map<String,String> statusList = projectService.statusList();
        model.addAttribute("statusList", statusList);

        if(keyword == null && status == null){
            model.addAttribute("projects", projects);
        }
        else{
            try {
                List<Project> projectSearchList = projectService.search(keyword,status);
                model.addAttribute("projects", projectSearchList);
                model.addAttribute("keywordSearch", keyword);
                model.addAttribute("statusSearch",status);
            }catch (ProjectNotExistsException e){
                e.printStackTrace();
                projects.clear();
                model.addAttribute("projects", projects);
                model.addAttribute("keywordSearch", keyword);
                model.addAttribute("statusSearch",status);
                return "projectlist";
            }
        }
        return "projectlist";
    }

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

        Integer version = 1;
        Set<Employee> project_employee = projectService.convertStringToEmployeeSet(member);
        Group group = groupService.findById(group_id);
        Project project = new Project(version, projectnumber,name,customer,status,startDate,endDate,group, project_employee);

        projectService.save(project);
        return "redirect:/projectlist";
    }

    @RequestMapping(value = {"/editproject/{id}"}, method = RequestMethod.GET)
    public String editProject(Model model, @PathVariable Long id){
        try {
            Project project = projectService.findById(id);
            Map<String, String> statusList = projectService.statusList();
            List<Employee> employees = employeeService.findAll();
            List<Group> groups = groupService.findAll();
            Set<Employee> memberSet = project.getEmployees();
            String memberInput = projectService.convertEmployeeSetToString(memberSet);

            model.addAttribute("members", memberInput);
            model.addAttribute("project", project);
            model.addAttribute("groups", groups);
            model.addAttribute("statusList", statusList);
            model.addAttribute("employees", employees);
            return "editproject";
        }
        catch (ProjectNotExistsException e){
            e.printStackTrace();
            return "redirect:/";
        }
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
                              @RequestParam(name = "endDate") Date endDate,
                              @RequestParam(name = "version") Integer version){
        //============================
        Set<Employee> project_employee = projectService.convertStringToEmployeeSet(member);
        Group group = groupService.findById(group_id);
        System.out.println(project_employee);
        Project project = new Project(id,version,projectnumber,name,customer,status,startDate,endDate,group,project_employee);

        projectService.edit(project);
        return "redirect:/projectlist";
    }


    @RequestMapping(value = "/deleteproject", method = RequestMethod.POST)
    public String deleteProject(@RequestParam(name = "id") Long id){
        try {
            Project project = projectService.findById(id);
            projectService.deleteById(id);
            return "redirect:/projectlist";
        }catch (ProjectNotExistsException e){
            e.printStackTrace();
            return "redirect:/projectlist";
        }
    }

    @RequestMapping(value = "/deleteselectedproject", method = RequestMethod.POST)
    public void deleteSelectedProject(@RequestParam(name = "id") Long id){
        try {
            Project project = projectService.findById(id);
            projectService.deleteById(id);
        }catch (ProjectNotExistsException e){
            e.printStackTrace();
        }
    }



    @RequestMapping(value = "/createproject/is-available-projectnumber", method = RequestMethod.GET)
    public @ResponseBody boolean checkProjectNumber(@RequestParam(name = "projectnumber") Integer projectnumber) throws ProjectNumberAlreadyExistsException{
        try {
            projectService.findByProjectNumber(projectnumber);
            return true;
        }
        catch (ProjectNumberAlreadyExistsException e){
            e.printStackTrace();
            return false;
        }
    }
}
