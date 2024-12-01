package com.lx.framework.demo1.fegin.controller;

import com.lx.framework.demo1.fegin.servcie.Demo2Fegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 测试fegin调用
 * @return: null
 * @author xin.liu
 * @date  13:50
 */
@RestController
@RequestMapping("/fegin")
public class FeginController {

    @Autowired
    private Demo2Fegin demo2Fegin;

    @PostMapping("/test")
    public Object test() {
        return demo2Fegin.test();
    }

}
