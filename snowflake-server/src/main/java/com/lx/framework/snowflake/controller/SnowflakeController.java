package com.lx.framework.snowflake.controller;

import com.lx.framework.snowflake.utils.SnowflakeIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xin.liu
 * @description 基于redis的分布式发号器
 * @date 2024-02-28  14:03
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/snowflake")
public class SnowflakeController {

    @GetMapping("/getId")
    public Long getId() {
       return SnowflakeIdUtil.nextId();
    }

}