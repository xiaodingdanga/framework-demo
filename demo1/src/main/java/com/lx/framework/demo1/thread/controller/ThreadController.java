package com.lx.framework.demo1.thread.controller;

import com.lx.framework.demo1.thread.servcie.AsyncService;
import com.lx.framework.tool.startup.handler.customException.AsyncException;
import com.lx.framework.tool.utils.enums.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

/**
 * @author xin.liu
 * @description 线程池以及异常拦截
 * @date 2024-03-05  23:26
 * @Version 1.0
 */
//@Slf4j
@RestController
public class ThreadController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/async")
    public String asyncTest1() {
        System.out.println(">>>>>>>>>>>>>>>>>>asyncTest1Thread: " + Thread.currentThread().getName());
        try {
            asyncService.asyncOne("123");
        }catch (Exception e){
//            e.printStackTrace();
            throw new AsyncException(CodeEnum.ASYNC_ERROR);
        }
//        asyncService.asyncTwo();
//        try {
//            //只有get才会捕获到异常
//            asyncService.asyncThree().get();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new AsyncException(CodeEnum.ERROR.getCode(), e.getMessage());
//        }
        return "123";
    }

    public static void main(String[] args) throws InterruptedException {
        long l = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Thread thread1 = new Thread(countDownLatch::countDown);
        Thread thread2 = new Thread(countDownLatch::countDown);
        Thread thread3 = new Thread(()->{
            try {
                Thread.sleep(3000);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();
        countDownLatch.await();
        long l1 = System.currentTimeMillis();
        System.out.println("所有线程以结束:"+(l1-l));


    }


}