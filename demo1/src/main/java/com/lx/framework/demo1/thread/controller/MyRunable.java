package com.lx.framework.demo1.thread.controller;

public class MyRunable implements Runnable {

    private int count = 0;


    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (count < 100) {
                    System.out.println(Thread.currentThread().getName() + "-->" + count);
                    count++;
                } else {
                    break;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
