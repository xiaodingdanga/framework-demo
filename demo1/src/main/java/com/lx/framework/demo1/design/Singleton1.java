package com.lx.framework.demo1.design;

public class Singleton1 {

    private Singleton1() {

    }

    private static class SingletonHolder {
        private static final Singleton1 INSTANCE = new Singleton1();
    }

    public static Singleton1 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
