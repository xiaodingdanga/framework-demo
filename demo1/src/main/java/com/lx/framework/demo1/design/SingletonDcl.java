package com.lx.framework.demo1.design;

public class SingletonDcl {

    private static volatile SingletonDcl instance;

    private SingletonDcl() {
    }

    public static SingletonDcl getInstance() {
        if (instance == null) { // 第一次检查
            synchronized (SingletonDcl.class) { // 加锁
                if (instance == null) { // 第二次检查
                    instance = new SingletonDcl(); // 创建实例
                }
            }
        }
        return instance;
    }

    public void doSomething() {
        System.out.println("do something");
    }
}
