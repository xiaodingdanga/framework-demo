package com.lx.framework.demo1.project.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.lx.framework.demo1.project.entity.ProjectLocation;
import com.lx.framework.demo1.project.mapper.ProjectLocationMapper;
import com.lx.framework.demo1.project.service.ProjectLocationService;
import com.lx.framework.demo1.project.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* 项目位置表
*
* @author xin.liu
* @since 2024-11-20
*/
@Slf4j
@Service
public class ProjectLocationServiceImpl extends ServiceImpl<ProjectLocationMapper, ProjectLocation> implements ProjectLocationService {

    @Autowired
    ProjectLocationMapper projectLocationMapper;

    @Autowired
    private ProjectService projectService;

    @Override
    public ProjectLocation getProjectLocation(Integer id){
        return projectLocationMapper.selectById(id);
    }

    @Override
    public List<ProjectLocation> getAllProjectLocation(){
        return projectLocationMapper.selectList(null);
    }

    @Override
    public void add(ProjectLocation projectLocation) {
        projectLocationMapper.insert(projectLocation);
    }

    @Override
    public int modify(ProjectLocation projectLocation) {
        return  projectLocationMapper.updateById(projectLocation);
    }

    @Override
    public void remove(String ids) {
        if(StringUtils.isNotEmpty(ids)){
            String[] array = ids.split(",");
            if (!CollectionUtils.isEmpty(Arrays.asList(array))) {
                projectLocationMapper.deleteBatchIds(Arrays.asList(array));
            }
        }
    }

    @Override
    public void test() {
        StopWatch started = StopWatch.createStarted();
        List<ProjectLocation> projectLocationList = buildProjectLocation(100000);
        //批量插入方法
        this.saveBatch(projectLocationList);
        log.info("插入百万数据耗时：{}s", started.getTime() / 1000);
    }

    @Override
    public void saveProjectLocationWithThread() {
        StopWatch started = StopWatch.createStarted();
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        List<ProjectLocation> projectLocationList = buildProjectLocation(100000);
        int dataSize = projectLocationList.size();
        int step = 1000;
        int totalTasks = dataSize % step == 0 ? dataSize / step : (dataSize / step + 1);
        //批量插入方法
        CountDownLatch countDownLatch = new CountDownLatch(totalTasks);
        for (int j = 0; j < dataSize; j = j + step) {
            final int start = j;
            final int perCount = (dataSize - start) < step ? (dataSize - start) : step;
            executorService.execute(() -> {
                try {
                    log.info("多线程开始：start == " + start + ",多线程个数count" + perCount);
                    this.saveBatch(projectLocationList.subList(start,perCount+start));
//                    projectService.saveBatch(projectList.subList(start,perCount+start));
                    countDownLatch.countDown();
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
        try {
            countDownLatch.await();
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("插入百万数据耗时：{}s", started.getTime() / 1000);
    }

    private  List<ProjectLocation> buildProjectLocation(int dateSize) {
        List<ProjectLocation> projectLocationList = Lists.newArrayList();
//        String noPrefix = RandomUtil.randomNumbers(3);
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        Snowflake snowflake = new Snowflake(1, 1);
        long l = snowflake.nextId();
        for (int i = 1; i < dateSize+1; i++) {
            ProjectLocation projectLocation = new ProjectLocation();
            projectLocation.setProjectId(l);
            String noPrefix = RandomUtil.randomString(upperCaseChars,1);
            projectLocation.setProvinceId(noPrefix + i);
            projectLocation.setCityId(noPrefix + i);
            projectLocationList.add(projectLocation);
        }
        return projectLocationList;
    }

    public static void main(String[] args) throws InterruptedException {
        //10次for循环
        for (int i = 0; i < 10; i++) {
            // 定义一个大写字母的字符数组
            String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String randomString = RandomUtil.randomString(upperCaseChars,1);
            System.out.println(randomString);
        }

    }
}
