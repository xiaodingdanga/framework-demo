package com.lx.framework.demo1.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.framework.demo1.project.entity.Project;

import java.util.List;

/**
* @author xin.liu
* @since 2024-11-20
*/
public interface ProjectService extends IService<Project> {

    /**
    * Project
    * @param
    * @return
    */
    Project getProject(Integer id);

    /**
    * Project
    * @param
    * @return
    */
    List<Project> getAllProject();

    /**
    * Project
    * @param project
    * @return
    */
    void add(Project project);

    /**
    * Project
    * @param project
    * @return
    */
    int modify(Project project);

    /**
    * Project
    * @param ids
    * @return
    */
    void remove(String ids);
}


