//package com.lx.framework.demo1.sms.controller;
//
//import com.ssc.hn.dba.sms.config.SmsTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.concurrent.ExecutionException;
//
//
///**
// * @author xin.liu
// * @description 封装短信发送starter
// * @date 2024-02-28  14:03
// * @Version 1.0
// */
//@Slf4j
//@RestController
//@RequestMapping(value = "/sms")
//public class SmsTestController {
//
//    @Value("${test:NULL}")
//    private String test;
//
//    @Autowired
//    private SmsTemplate smsTemplate;
//
//    @GetMapping("/send")
//    public String  send() throws ExecutionException, InterruptedException {
//        return smsTemplate.send("15845256867", "123456");
//    }
//
//    @GetMapping("/testValue")
//    public String  testValue() {
//        String str = test;
//        return str;
//    }
//
//    @GetMapping("/testLog")
//    public String  testLog() {
//        for (int i = 0; i < 10; i++) {
//                log.info("info生效:{}",i);
//                log.warn("warn生效:{}",i);
//                log.error("error生效:{}",i);
//        }
//
//        return "12312";
//    }
//}