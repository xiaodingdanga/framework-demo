package com.lx.framework.demo1.project.controller;

import com.lx.framework.demo1.project.entity.ProjectLocation;
import com.lx.framework.demo1.project.service.ProjectLocationService;
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

@RequestMapping("/project-location")
public class ProjectLocationController {

    @Autowired
    private ProjectLocationService projectLocationService;

    @GetMapping("/selectOne")
    public ProjectLocation getProjectLocation(@RequestParam("id") Integer id){
        ProjectLocation projectLocationOne = projectLocationService.getProjectLocation(id);
        return projectLocationOne;
    }

    @GetMapping("/listAll")
    public List<ProjectLocation> getAllProjectLocation(){
        List<ProjectLocation> projectLocationList = projectLocationService.getAllProjectLocation();
        return projectLocationList;
    }

    @PostMapping("/add")
    public Object add(@Validated @RequestBody ProjectLocation projectLocation) {
        projectLocationService.add( projectLocation);
        return null;
    }

    @PutMapping("/update")
    public int update(@Validated @RequestBody ProjectLocation projectLocation) {
        int num = projectLocationService.modify(projectLocation);
        return num;
    }

    @DeleteMapping(value = "/delete/{ids}")
    public Object remove(@NotBlank(message = "{required}") @PathVariable String ids) {
        projectLocationService.remove(ids);
        return null;
    }

    @PostMapping("/test")
    public String test() {
        projectLocationService.test();
        return "success";
    }
    @PostMapping("/test1")
    public String saveProjectLocationWithThread() {
        projectLocationService.saveProjectLocationWithThread();
        return "success";
    }
}
