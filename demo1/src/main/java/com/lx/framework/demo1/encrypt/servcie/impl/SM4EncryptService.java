package com.lx.framework.demo1.encrypt.servcie.impl;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.github.pagehelper.util.StringUtil;
import com.lx.framework.demo1.encrypt.servcie.EncryptService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-04-02  09:51
 * @Version 1.0
 */
@Service
public class SM4EncryptService implements EncryptService {


    private SymmetricCrypto SM4Client = null;

    @Value("${sm4.public.key}")
    private String publicKey;


    private SymmetricCrypto getSm2Client() {
        if (SM4Client == null) {
            synchronized (this) {
                if (SM4Client == null) {
                    try {
                        SM4Client = SmUtil.sm4(publicKey.getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return SM4Client;
    }

    /**
     * 公钥加密
     *
     * @param parameter
     * @return
     */
    @Override
    public String encrypt(String parameter) {
        if (StringUtil.isEmpty(parameter)) {
            return null;
        }
        return getSm2Client().encryptHex(parameter);
    }

    /**
     * 私钥解密
     *
     * @param columnValue
     * @return
     */
    @Override
    public Object decrypt(String columnValue) {
        if (StringUtil.isEmpty(columnValue)) {
            return null;
        }
        return getSm2Client().decryptStr(columnValue);
    }
}
