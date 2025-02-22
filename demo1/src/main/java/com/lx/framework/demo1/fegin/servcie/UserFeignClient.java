package com.lx.framework.demo1.fegin.servcie;

import com.lx.framework.demo1.fegin.entity.SysUserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "demo2")
public interface UserFeignClient {


    @PostMapping(value = "/core/stock/reduct")
    SysUserEntity reduct(@RequestBody SysUserEntity sysUserEntity);

    @PostMapping(value = "/core/stock/reduct1")
    Boolean reduct1();

}
