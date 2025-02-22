package com.lx.framework.demo1.user1.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.framework.demo1.fegin.entity.SysUserEntity;
import com.lx.framework.demo1.fegin.servcie.UserFeignClient;
import com.lx.framework.demo1.user.entity.UserInfo;
import com.lx.framework.demo1.user.servcie.UserInfoService;
import com.lx.framework.demo1.user.servcie.UserInfoTccService;
import com.lx.framework.demo1.user1.entity.User;
import com.lx.framework.demo1.user1.mapper.UserMapper;
import com.lx.framework.demo1.user1.service.UserService;
import feign.FeignException;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
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
    private UserInfoService userInfoService;

    @Resource
    private UserFeignClient userFeignClient;

    @Autowired
    private UserInfoTccService userInfoTccService;


    @Override
    public User getUser(Integer id){
        return this.getBaseMapper().selectById(id);
    }

    @Override
    public List<User> getAllUser(){
        return this.getBaseMapper().selectList(null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(User user) {
        this.getBaseMapper().insert(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1L);
        userInfo.setUserName("test");
        userInfo.setPlatformCode("test");
        userInfoService.add(userInfo);

//        UserInfo userInfo1 = new UserInfo();
//        userInfo1.setUserId(2L);
//        userInfo1.setUserName("test");
//        userInfo1.setPlatformCode("test");
//        userInfoService.add1(userInfo1);
    }

    @Override
    public int modify(User user) {
        return  this.getBaseMapper().updateById(user);
    }

    @Override
    public void remove(String ids) {
        if(StringUtils.isNotEmpty(ids)){
            String[] array = ids.split(",");
            if (!CollectionUtils.isEmpty(Arrays.asList(array))) {
                this.getBaseMapper().deleteBatchIds(Arrays.asList(array));
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

    @Override
    @GlobalTransactional
    public void tcc() throws InterruptedException {
        System.out.println("开始事物："+ RootContext.getXID());
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(1L);
        userInfo.setUserName("test");
        userInfo.setPlatformCode("test");
        try {
            UserInfo userInfo1 = userInfoTccService.prepareSaveOrder(userInfo, 1L);
        }catch (Exception e){
            throw new RuntimeException("模拟异常1");
        }
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId("044d1a8a68084e979dccddc812b03200");
        Boolean b = userFeignClient.reduct1();
        if (!b){
            throw new RuntimeException("模拟异常2");
        }
//        Thread.sleep(10000);
    }
}
