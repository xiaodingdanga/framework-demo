package com.lx.framework.demo1.user.controller;

import com.lx.framework.demo1.user.entity.UserInfo;
import com.lx.framework.demo1.user.pojo.vo.DemoVo;
import com.lx.framework.demo1.user.servcie.UserInfoService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @description mybatis验证
 * @return: null
 * @author xin.liu
 * @date  13:56
 */
@RestController

@RequestMapping("/user-info")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/selectOne")
    public UserInfo getUserInfo(@RequestParam("id") Integer id){
        UserInfo userInfoOne = userInfoService.getUserInfo(id);
        return userInfoOne;
    }

    @GetMapping("/listAll")
    public List<UserInfo> getAllUserInfo(){
        List<UserInfo> userInfoList = userInfoService.getAllUserInfo();
        return userInfoList;
    }

    @PostMapping("/add")
    public Object add(@RequestBody UserInfo userInfo) {
        userInfoService.add(userInfo);
        return null;
    }

    @PutMapping("/update")
    public int update(@Validated @RequestBody UserInfo userInfo) {
        int num = userInfoService.modify(userInfo);
        return num;
    }

    @DeleteMapping(value = "/delete/{ids}")
    public Object remove(@NotBlank(message = "{required}") @PathVariable String ids) {
        userInfoService.remove(ids);
        return null;
    }

    @GetMapping("/insert1")
    public void insert1(){
        userInfoService.insert1();
    }

    @GetMapping("/test")
    public DemoVo test(){
        DemoVo demoVo = new DemoVo();
        demoVo.setStr1("1");
        return demoVo;
    }
}
