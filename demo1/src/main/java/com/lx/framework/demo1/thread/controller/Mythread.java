package com.lx.framework.demo1.thread.controller;

import java.util.ArrayList;
import java.util.Collections;

public class Mythread extends Thread {
    public static ArrayList<Integer> list;



    public Mythread(ArrayList<Integer> list) {
        this.list = list;
    }


    @Override
    public void run() {
        while (true) {
            synchronized (Mythread.class) {
                if (list.size() == 0) {
                    break;
                } else {
                    Collections.shuffle(list);
                    Integer remove = list.removeFirst();
                    System.out.println(Thread.currentThread().getName() + "-->" + remove);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

//    @Override
//    public void run() {
//        while (true) {
//
//            if (list.size() == 0) {
//                break;
//            } else {
//                Collections.shuffle(list);
//                Integer remove = list.removeFirst();
//                System.out.println(Thread.currentThread().getName() + "-->" + remove);
//                Thread.sleep(10);
//
//            }
//        }
//    }
}
