package com.lx.framework.demo1.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

/**
 * @author xin.liu
 * @description TODO
 * @date 2025-05-15  17:56
 * @Version 1.0
 */
public class SM4Utils {

    private static String publicKey = "698QGB9jn9y0iPbe";

    // 使用 volatile 关键字确保多线程环境下的可见性和禁止指令重排序
    private static volatile SymmetricCrypto SM4Client;

    // 私有构造函数，防止外部实例化
    private SM4Utils() {}

    // 获取单例实例的方法
    public static SymmetricCrypto getSM4Client() {
        if (SM4Client == null) { // 第一次检查
            synchronized (SM4Utils.class) { // 加锁确保只有一个线程进入
                if (SM4Client == null) { // 第二次检查
                    SM4Client = SmUtil.sm4(publicKey.getBytes()); // 创建实例
                }
            }
        }
        return SM4Client;
    }

    /**
     * 公钥加密
     *
     * @param data
     * @return
     */
    public static String enCode(String data) {
        if (null == data || "".equals(data)) {
            return data;
        }
        try {
            getSM4Client().decryptStr(data);
        } catch (Exception e) {
            data = getSM4Client().encryptHex(data);
        }
        return data;
    }

    /**
     * 私钥解密
     *
     * @param data
     * @return
     */
    public static String deCode(String data) {
        if (null == data || "".equals(data)) {
            return data;
        }
        try {
            data = getSM4Client().decryptStr(data);
        } catch (Exception e) {
        }
        return data;
    }

    public static void main(String[] args) {
        String data = "123";
        SymmetricCrypto sm4 = SmUtil.sm4("698QGB9jn9y0iPbe".getBytes());
        //公钥加密
        try {
            SM4Utils.getSM4Client().decryptStr(data);
        } catch (Exception e) {
            data = SM4Utils.getSM4Client().encryptHex(data);
        }
        System.out.println(data);

        //私钥解密
        try {
            data =  SM4Utils.getSM4Client().decryptStr(data);
        } catch (Exception e) {

        }

        System.out.println(data);
        System.out.println(sm4.encryptHex("13810351781"));

        System.out.println(sm4.encryptHex(SecureUtil.md5("654321")));
        System.out.println(SecureUtil.md5("654321"));
        System.out.println(System.currentTimeMillis());


    }

}