package com.lx.framework.demo1.thread.servcie.impl;

import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.lx.framework.demo1.thread.servcie.AsyncService;
import com.lx.framework.demo1.user.entity.UserInfo;
import com.lx.framework.demo1.user.servcie.UserInfoService;
import com.lx.framework.tool.startup.handler.customException.AsyncException;
import com.lx.framework.tool.utils.enums.CodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-03-09  17:25
 * @Version 1.0
 */
@Service
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    private UserInfoService userInfoService;

    @Async
    @Override
    public void asyncOne(String param) {
        System.out.println(">>>>>>>>>>>>>>>>>>asyncOneThread: " + Thread.currentThread().getName());
        try {
            Thread.sleep(500);
//            int i = 1/0;
        } catch (Exception e) {
//            e.printStackTrace();
            //todo 异常信息记录 用于后续处理异常线程数据  也可以在异常信息过滤其中处理
            throw new AsyncException(CodeEnum.ASYNC_ERROR);
        }
        System.out.println("asyncOne业务执行完毕");
    }

    @Async("customizeThreadPool")
    @Override
    public void asyncTwo() {
        System.out.println(">>>>>>>>>>>>>>>>>>asyncTwoThread: " + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
            int i = 1/0;
        } catch (InterruptedException e) {
            throw new  AsyncException(CodeEnum.ASYNC_ERROR);
        }
        System.out.println("asyncTwo业务执行完毕");
    }

    @Async()
    @Override
    public Future<String> asyncThree(){
        System.out.println(">>>>>>>>>>>>>>>>>>asyncThreeThread: " + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
            int i = 1/0;
        } catch (Exception e) {
//            e.printStackTrace();
            //todo 异常信息记录
            throw new AsyncException(CodeEnum.ERROR);
        }
        System.out.println("asyncThree业务执行完毕");
        return new AsyncResult<>("任务三完成"+System.currentTimeMillis());
    }

    public static Integer count = 0;
    public static Integer count1 = 0;

    @Override
    public void synchronizedBuy() {
        synchronized (count){
            count++;
            System.out.println(">>>>>>>>>>>>>>>>>>synchronizedBuyThread: " + Thread.currentThread().getName()+":"+count);
        }
        count1++;
        System.out.println("----------------------synchronizedBuyThread: " + Thread.currentThread().getName()+":"+count1);
//        boolean update = new LambdaUpdateChainWrapper<>(userInfoService.getBaseMapper()).eq(UserInfo::getUserInfoId, 1).setSql("user_status = user_status + 1").update();
    }

    @Override
    public void buy() {
        count++;
        System.out.println(">>>>>>>>>>>>>>>>>>synchronizedBuyThread: " + Thread.currentThread().getName()+":"+count);
//        boolean  update= new LambdaUpdateChainWrapper<>(userInfoService.getBaseMapper()).eq(UserInfo::getUserInfoId, 1).setSql("user_status = user_status + 1").update();
    }

    private final Lock lock = new ReentrantLock();

    @Override
    public void reentrantLockBuy() {
        lock.lock();
        count++;
        System.out.println(">>>>>>>>>>>>>>>>>>reentrantLockBuyThread: " + Thread.currentThread().getName()+":"+count);
        lock.unlock();
    }

    public synchronized static void staticBuy() {
        count++;
        System.out.println(">>>>>>>>>>>>>>>>>>staticBuyThread: " + Thread.currentThread().getName()+":"+new AsyncServiceImpl().count);
    }

    public static Map<String, String> cache = new HashMap<>();

    // 静态方法，确保线程安全
    public String getFromCache(String key) {
//        if (!cache.containsKey(key)) {
//            System.out.println("缓存中不存在，加载数据...");
//            cache.put(key, "数据" + key); // 模拟加载数据
//        }
//        return cache.get(key);
        for (Map.Entry<String, String> stringStringEntry : cache.entrySet()) {
            System.out.println(stringStringEntry.getKey()+":"+stringStringEntry.getValue());
        }
        System.out.println("缓存中存在数量："+cache.size());
        return "success";
    }

    public synchronized void updateCache(String key, String value) {
        cache.put(key, value);
        System.out.println("缓存更新：" + key + " -> " + value);
    }
}