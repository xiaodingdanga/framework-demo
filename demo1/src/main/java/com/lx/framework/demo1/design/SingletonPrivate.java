package com.lx.framework.demo1.design;

public class SingletonPrivate {

    private SingletonPrivate() {

    }

    private static class SingletonHolder {
        private static final SingletonPrivate INSTANCE = new SingletonPrivate();
    }

    public static SingletonPrivate getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
