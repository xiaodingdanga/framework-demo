package com.lx.framework.demo1.thread.controller;

import com.lx.framework.demo1.thread.servcie.AsyncService;
import com.lx.framework.tool.startup.handler.customException.AsyncException;
import com.lx.framework.tool.utils.enums.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            for (int i = 0; i < 1000; i++) {
                asyncService.asyncOne("123");
            }
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

    @GetMapping("/newFixedThreadPool")
    public String newFixedThreadPool() {
        long l = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        for (int i = 0; i < 10000; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executorService.close();
        long l1 = System.currentTimeMillis();
        return "耗时："+(l1-l);
    }

    @GetMapping("/newVirtualThreadPerTaskExecutor")
    public String newVirtualThreadPerTaskExecutor() {
        long l = System.currentTimeMillis();
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < 10000; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executorService.close();
        long l1 = System.currentTimeMillis();
        return "耗时："+(l1-l);
    }


    public static void main(String[] args) throws InterruptedException {
//        //countDownLatch
//        long l = System.currentTimeMillis();
//        CountDownLatch countDownLatch = new CountDownLatch(3);
//        Thread thread1 = new Thread(countDownLatch::countDown);
//        Thread thread2 = new Thread(countDownLatch::countDown);
//        Thread thread3 = new Thread(()->{
//            try {
//                Thread.sleep(3000);
//                countDownLatch.countDown();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        countDownLatch.await();
//        long l1 = System.currentTimeMillis();
//        System.out.println("所有线程以结束:"+(l1-l));

        //虚拟线程和传统线程池对比
        long l = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        for (int i = 0; i < 10000; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executorService.close();
        long l1 = System.currentTimeMillis();
        System.out.println("耗时："+(l1-l));

        ExecutorService executorService1 = Executors.newVirtualThreadPerTaskExecutor();
        for (int i = 0; i < 10000; i++) {
            executorService1.submit(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executorService1.close();
        long l2 = System.currentTimeMillis();
        System.out.println("耗时："+(l2-l));


    }


}