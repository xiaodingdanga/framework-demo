package com.lx.framework.demo1.project.controller;

import com.lx.framework.demo1.project.entity.Project;
import com.lx.framework.demo1.project.service.ProjectService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
* @author xin.liu
* @since 2024-11-20
*/
@RestController

@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/selectOne")
    public Project getProject(@RequestParam("id") Integer id){
        Project projectOne = projectService.getProject(id);
        return projectOne;
    }

    @GetMapping("/listAll")
    public List<Project> getAllProject(){
        List<Project> projectList = projectService.getAllProject();
        return projectList;
    }

    @PostMapping("/add")
    public Object add(@Validated @RequestBody Project project) {
        projectService.add( project);
        return "成功";
    }

    @PutMapping("/update")
    public int update(@Validated @RequestBody Project project) {
        int num = projectService.modify(project);
        return num;
    }

    @DeleteMapping(value = "/delete/{ids}")
    public Object remove(@NotBlank(message = "{required}") @PathVariable String ids) {
        projectService.remove(ids);
        return null;
    }
}
