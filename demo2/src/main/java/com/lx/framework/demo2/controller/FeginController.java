package com.lx.framework.demo2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-03-23  14:59
 * @Version 1.0
 */
@RestController
@RequestMapping("/fegin")
public class FeginController {

    @GetMapping("/test")
    public String test(){
        return "123";
    }
}