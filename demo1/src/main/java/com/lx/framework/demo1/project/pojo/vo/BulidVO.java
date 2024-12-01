package com.lx.framework.demo1.project.pojo.vo;

import com.lx.framework.demo1.project.entity.Project;
import com.lx.framework.demo1.project.entity.ProjectLocation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-11-20  17:33
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class BulidVO {

    private List<Project> projectList;

    private List<ProjectLocation> projectLocationList;

}