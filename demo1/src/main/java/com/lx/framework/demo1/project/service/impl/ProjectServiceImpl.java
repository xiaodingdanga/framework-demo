package com.lx.framework.demo1.project.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.framework.demo1.project.entity.Project;
import com.lx.framework.demo1.project.mapper.ProjectMapper;
import com.lx.framework.demo1.project.service.ProjectService;
import com.lx.framework.demo1.utils.SnowflakeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.infra.hint.HintManager;
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
        HintManager instance = HintManager.getInstance();
        instance.addTableShardingValue("project", 1);
        List<Project> list = new LambdaQueryChainWrapper<>(this.getBaseMapper()).eq(Project::getId,1L).eq(Project::getUserId, 1L).list();
//        list.forEach(Project -> {
//            System.out.println(JSONObject.toJSONString(Project));
//        });
        //即时关闭 保证线程安全
        instance.close();
        return list;
    }

    @Override
    public void add(Project project1) {
        int a = 1;
        for (int j = 1; j <= 10; j++){
            for (int i = 1; i <= 10; i++){
                Project project = new Project();
//                System.out.println("项目:第"+j+"人的第"+i+"个项目");
                project.setId(Long.valueOf(a));
                project.setUserId(Long.valueOf(j));
                project.setProjectName("项目:第"+j+"人的第"+i+"个项目");
                projectMapper.insert(project);
                a++;
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
