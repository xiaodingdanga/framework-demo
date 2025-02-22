package com.lx.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-02-28  14:40
 * @Version 1.0
 */
@EnableAsync
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class Demo1Application {
    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class,args);
    }

}