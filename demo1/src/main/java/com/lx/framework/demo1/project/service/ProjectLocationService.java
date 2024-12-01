package com.lx.framework.demo1.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.framework.demo1.project.entity.ProjectLocation;

import java.util.List;

/**
* @author xin.liu
* @since 2024-11-20
*/
public interface ProjectLocationService extends IService<ProjectLocation> {

    /**
    * ProjectLocation
    * @param
    * @return
    */
    ProjectLocation getProjectLocation(Integer id);

    /**
    * ProjectLocation
    * @param
    * @return
    */
    List<ProjectLocation> getAllProjectLocation();

    /**
    * ProjectLocation
    * @param projectLocation
    * @return
    */
    void add(ProjectLocation projectLocation);

    /**
    * ProjectLocation
    * @param projectLocation
    * @return
    */
    int modify(ProjectLocation projectLocation);

    /**
    * ProjectLocation
    * @param ids
    * @return
    */
    void remove(String ids);

    void test();

    void saveProjectLocationWithThread();
}


