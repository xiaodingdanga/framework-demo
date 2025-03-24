package com.lx.framework.demo2.controller;


import com.lx.framework.demo2.dao.entity.SysUserEntity;
import com.lx.framework.demo2.service.SysUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2023-08-14
 */
@RestController
@RequestMapping("/core/stock")
public class StockController {

    @Resource
    private SysUserService sysUserService;

    @PostMapping(value = "/reduct")
    public SysUserEntity reduct(@RequestBody SysUserEntity sysUserEntity) {
        sysUserService.reduct(sysUserEntity.getId());
        return  new SysUserEntity();
    }

    @PostMapping(value = "/reduct1")
    public Boolean reduct1() throws Exception {
        return  sysUserService.reduct1();
    }


}
