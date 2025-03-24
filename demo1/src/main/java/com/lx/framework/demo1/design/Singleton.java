package com.lx.framework.demo1.design;

public class Singleton {



    private static Singleton instance = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return instance;
    }

    public void doSomething() {
        System.out.println("do something");
    }
}
