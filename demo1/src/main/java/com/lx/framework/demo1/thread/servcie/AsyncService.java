package com.lx.framework.demo1.thread.servcie;

import java.util.concurrent.Future;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-03-09  17:24
 * @Version 1.0
 */
public interface AsyncService {

    public void asyncOne(String param);

    public void asyncTwo();

    public Future<String> asyncThree();

}