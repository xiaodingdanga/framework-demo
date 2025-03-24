package com.lx.framework.demo1.thread.controller;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mythread2 extends Thread {

    static int count = 0;

    static Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                if (count < 100) {
                    System.out.println(Thread.currentThread().getName() + "-->" + count);
                    count++;
                    Thread.sleep(10);
                } else {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
