package com.lx.framework.demo1.jvm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

/**
 * @author xin.liu
 * @description 线程池以及异常拦截
 * @date 2024-03-05  23:26
 * @Version 1.0
 */
//@Slf4j
@RestController
public class JVMController {


    private static HashSet hashSet = new HashSet();

    /**
     * 不断的向 hashSet 集合添加数据
     */
    @GetMapping("/addHashSetThread")
    public String addHashSetThread() {
        // 初始化常量
        new Thread(() -> {
            int count = 0;
            while (true) {
                try {
                    hashSet.add("count" + count);
                    Thread.sleep(1000);
                    count++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return "123";
    }

    /**
     * 模拟 CPU 过高
     */
    @GetMapping("/cpuHigh")
    public String cpuHigh() {
        new Thread(() -> {
            while (true) {

            }
        }).start();
        return "123";
    }

    /**
     *  模拟线程死锁
     */
    @GetMapping("/deadThread")
    public String deadThread() {
        /** 创建资源 */
        Object resourceA = new Object();
        Object resourceB = new Object();
        // 创建线程
        Thread threadA = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread() + " get ResourceA");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resourceB");
                synchronized (resourceB) {
                    System.out.println(Thread.currentThread() + " get resourceB");
                }
            }
        });

        Thread threadB = new Thread(() -> {
            synchronized (resourceB) {
                System.out.println(Thread.currentThread() + " get ResourceB");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resourceA");
                synchronized (resourceA) {
                    System.out.println(Thread.currentThread() + " get resourceA");
                }
            }
        });
        threadA.start();
        threadB.start();
        return "123";
    }

}