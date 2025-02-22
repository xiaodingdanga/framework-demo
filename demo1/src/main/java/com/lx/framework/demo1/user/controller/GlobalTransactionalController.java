package com.lx.framework.demo1.user.controller;

import com.lx.framework.demo1.user.entity.UserInfo;
import com.lx.framework.demo1.user.servcie.TccActionOneService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xxx")
public class GlobalTransactionalController {

    @Autowired
    private TccActionOneService tccActionOneService;

    @GetMapping(value = "/xxx")
    @GlobalTransactional
    public void globalTransactional() {
        System.out.println("开始事物："+ RootContext.getXID());
        //第一个TCC 事务参与者
        boolean b = tccActionOneService.tryDemo(new UserInfo(), "demoParamOne","demoParamTwo");
        if (!b){
            throw new RuntimeException("模拟异常1");
        }
        //第N个TCC 事务参与者
    }
}
