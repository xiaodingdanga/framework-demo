package com.lx.framework.demo1.thread.controller;

import com.lx.framework.demo1.thread.servcie.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xin.liu
 * @description 线程池以及异常拦截
 * @date 2024-03-05  23:26
 * @Version 1.0
 */
//@Slf4j
@RestController
public class JUCController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("/JUC")
    public String asyncTest1() {

        return "123";
    }

    public static void main(String[] args) {
//        ArrayList<Integer> integers = new ArrayList<>();
//        ArrayList<Integer> integer1 = new ArrayList<>();
//        integers.add(1);
//        integers.add(2);
//        integers.add(3);
//        integers.add(4);
//        integers.add(5);
//        integers.add(6);
//        integers.add(7);
//        integers.add(8);
//        integers.add(9);
//        integers.add(10);
//        integer1.add(1);
//        integer1.add(2);
//        integer1.add(3);
//        integer1.add(4);
//        integer1.add(5);
//        integer1.add(6);
//        integer1.add(7);
//        integer1.add(8);
//        integer1.add(9);
//        integer1.add(10);
//        Mythread mythread1 = new Mythread(integers);
//        Mythread mythread2 = new Mythread(integer1);
//        mythread1.setName("线程1");
//        mythread2.setName("线程2");
//        mythread1.start();
//        mythread2.start();


//        MyRunable myRunable = new MyRunable();
//
//        Thread thread1 = new Thread(myRunable);
//        Thread thread2 = new Thread(myRunable);
//        thread1.setName("线程1");
//        thread2.setName("线程2");
//        thread1.start();
//        thread2.start();


//        Mythread2 mythread1 = new Mythread2();
//        Mythread2 mythread2 = new Mythread2();
//        mythread1.setName("线程1");
//        mythread2.setName("线程2");
//        mythread1.start();
//        mythread2.start();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors() * 2,
                Runtime.getRuntime().availableProcessors() * 4,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(20),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        threadPoolExecutor.execute(() -> {

            System.out.println("123");


        });

    }

}