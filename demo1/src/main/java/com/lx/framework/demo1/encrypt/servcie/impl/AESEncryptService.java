package com.lx.framework.demo1.encrypt.servcie.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.lx.framework.demo1.encrypt.servcie.EncryptService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-04-02  09:51
 * @Version 1.0
 */
@Service
public class AESEncryptService implements EncryptService {


    /**
     * 线上运行后勿修改，会影响已加密数据解密
     */
    private static final byte[] KEYS = "9ee5543ce7bd002aaf9148613f228f8c".getBytes(StandardCharsets.UTF_8);
    // 构建
    private AES aes = SecureUtil.aes(KEYS);


    @Override
    public String encrypt(String parameter) {
        System.out.println("AES算法加密");
        return aes.encryptHex(parameter);
    }

    @Override
    public Object decrypt(String columnValue) {
        System.out.println("AES算法解密");
        return aes.decryptStr(columnValue);
    }
}
