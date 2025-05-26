package com.lx.framework.demo1.thread.servcie;

import java.util.concurrent.Future;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-03-09  17:24
 * @Version 1.0
 */
public interface AsyncService {

    void asyncOne(String param);

    void asyncTwo();

    Future<String> asyncThree();

    void synchronizedBuy();

    void buy();

    void reentrantLockBuy();

    public String getFromCache(String key);

    public void updateCache(String key, String value);

}