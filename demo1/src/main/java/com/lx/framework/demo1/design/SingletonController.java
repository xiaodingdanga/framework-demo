package com.lx.framework.demo1.design;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-03-23  15:37
 * @Version 1.0
 */
@RestController
@RequestMapping
public class SingletonController {

    @RequestMapping("/singleton")
    public void encrypt() {
        SingletonDcl instance = SingletonDcl.getInstance();
        System.out.println(instance);
        instance.doSomething();
    }

}