package com.lx.framework.demo1.encrypt.controller;

import com.lx.framework.demo1.encrypt.servcie.impl.AESEncryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-03-23  15:37
 * @Version 1.0
 */
@RestController
@RequestMapping("/encrypt")
public class EncryptController {

//    @Autowired
//    private EncryptService encryptService;

    @Autowired
    private AESEncryptService aesEncryptService;

    @RequestMapping("/encrypt")
    public void encrypt(){
        System.out.println(aesEncryptService.encrypt("123456"));
//        System.out.println(sm4EncryptService.encrypt("123456"));
    }
}