package com.lx.framework.demo1.encrypt.servcie;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-04-02  09:39
 * @Version 1.0
 */
public interface EncryptService {
    public String encrypt(String parameter);

    public Object decrypt(String columnValue);
}
