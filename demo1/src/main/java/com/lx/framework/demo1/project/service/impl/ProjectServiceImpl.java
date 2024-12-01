package com.lx.framework.demo1.project.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.framework.demo1.project.entity.Project;
import com.lx.framework.demo1.project.mapper.ProjectMapper;
import com.lx.framework.demo1.project.service.ProjectService;
import com.lx.framework.demo1.utils.SnowflakeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
* 项目位置表
*
* @author xin.liu
* @since 2024-11-20
*/
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    private SnowflakeUtils snowflakeUtils;

    @Override
    public Project getProject(Integer id){
        return projectMapper.selectById(id);
    }

    @Override
    public List<Project> getAllProject(){
        List<Project> list = new LambdaQueryChainWrapper<>(this.getBaseMapper()).eq(Project::getUserId, 97).list();
//        list.forEach(Project -> {
//            System.out.println(JSONObject.toJSONString(Project));
//        });
        return list;
    }

    @Override
    public void add(Project project) {
        int a = 1;
        for (int j = 1; j <= 100; j++){
            for (int i = 1; i <= 100; i++){
//                System.out.println("项目:第"+j+"人的第"+i+"个项目");
                a++;
                project.setId(Long.valueOf(a));
                project.setUserId(Long.valueOf(j));
                project.setProjectName("项目:第"+j+"人的第"+i+"个项目");
                projectMapper.insert(project);
            }
        }
    }

    @Override
    public int modify(Project project) {
        return  projectMapper.updateById(project);
    }

    @Override
    public void remove(String ids) {
        if(StringUtils.isNotEmpty(ids)){
            String[] array = ids.split(",");
            if (!CollectionUtils.isEmpty(Arrays.asList(array))) {
                projectMapper.deleteBatchIds(Arrays.asList(array));
            }
        }
    }
}
