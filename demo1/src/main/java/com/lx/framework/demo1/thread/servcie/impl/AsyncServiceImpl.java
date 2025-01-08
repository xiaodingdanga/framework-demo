package com.lx.framework.demo1.thread.servcie.impl;

import com.lx.framework.demo1.thread.servcie.AsyncService;
import com.lx.framework.tool.startup.handler.customException.AsyncException;
import com.lx.framework.tool.utils.enums.CodeEnum;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-03-09  17:25
 * @Version 1.0
 */
@Service
public class AsyncServiceImpl implements AsyncService {
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

}