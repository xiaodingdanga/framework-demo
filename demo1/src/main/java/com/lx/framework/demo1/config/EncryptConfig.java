package com.lx.framework.demo1.config;

import com.lx.framework.demo1.encrypt.servcie.EncryptService;
import com.lx.framework.demo1.encrypt.servcie.impl.AESEncryptService;
import com.lx.framework.demo1.encrypt.servcie.impl.SM4EncryptService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author xin.liu
 * @description TODO
 * @date 2024-04-02  09:44
 * @Version 1.0
 */
@Configuration
public class EncryptConfig {

//    @Value("${encrypt.algorithm}")
    private String algorithm = "SM4";


    @Bean
    @ConditionalOnMissingBean(EncryptService.class)
    public EncryptService encryptService() {
        EncryptService encryptService;
        System.out.println("-----------------"+algorithm);
        switch (algorithm) {
            case "AES":
                System.out.println(1);
                encryptService = new AESEncryptService();
                break;
            case "SM4":
                System.out.println(2);
                encryptService = new SM4EncryptService();
                break;
            default:
                encryptService =  null;
        }
        return encryptService;
    }
}
