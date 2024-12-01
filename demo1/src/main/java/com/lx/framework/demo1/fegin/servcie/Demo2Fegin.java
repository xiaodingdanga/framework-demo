package com.lx.framework.demo1.fegin.servcie;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-03-23  15:01
 * @Version 1.0
 */
@FeignClient(name = "demo1",url = "http://localhost:8081")
public interface Demo2Fegin {

    @GetMapping("/fegin/test")
    String test();
}