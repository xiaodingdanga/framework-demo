package com.lx.framework.demo1.user1.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.lx.framework.demo1.user1.entity.User;
import com.lx.framework.demo1.user1.mapper.UserMapper;
import com.lx.framework.demo1.user1.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* 
*
* @author xin.liu
* @since 2024-11-18
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUser(Integer id){
        return userMapper.selectById(id);
    }

    @Override
    public List<User> getAllUser(){
        return userMapper.selectList(null);
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public int modify(User user) {
        return  userMapper.updateById(user);
    }

    @Override
    public void remove(String ids) {
        if(StringUtils.isNotEmpty(ids)){
            String[] array = ids.split(",");
            if (!CollectionUtils.isEmpty(Arrays.asList(array))) {
                userMapper.deleteBatchIds(Arrays.asList(array));
            }
        }
    }

    @Override
    public void saveUserWithBatch() {
        List<User> UserList = buildUser(1000);
        log.info("插入百万数据耗时开始");
        StopWatch started = StopWatch.createStarted();
        //批量插入方法
        this.saveBatch(UserList);
        log.info("插入百万数据耗时：{}s", started.getTime() / 1000);
    }

    @Override
    public void saveUserWithThread() {
        StopWatch started = StopWatch.createStarted();
        ExecutorService executorService = Executors.newFixedThreadPool(12);
        List<User> UserList = buildUser(1000000);
        int dataSize = UserList.size();
        int step = 1000;
        int totalTasks = dataSize % step == 0 ? dataSize % step : (dataSize / step + 1);
        //批量插入方法
        CountDownLatch countDownLatch = new CountDownLatch(totalTasks);
        for (int j = 0; j < dataSize; j = j + step) {
            final int start = j;
            final int perCount = (dataSize - start) < step ? (dataSize - start) : step;
            executorService.execute(() -> {
                try {
                    log.info("多线程开始：start == +" + start + ",多线程个数count" + perCount);
                    this.saveBatch(UserList.subList(start,perCount+start));
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

    private List<User> buildUser(int dateSize) {
        List<User> UserList = Lists.newArrayList();
        String noPrefix = RandomUtil.randomNumbers(3);
        for (int i = 0; i < dateSize; i++) {
            User User = new User();
            User.setName(noPrefix + i);
            User.setSex(1);
            UserList.add(User);
        }
        return UserList;
    }
}