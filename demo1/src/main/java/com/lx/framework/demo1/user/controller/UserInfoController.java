package com.lx.framework.demo1.user.controller;

import com.lx.framework.demo1.user.entity.UserInfo;
import com.lx.framework.demo1.user.pojo.vo.DemoVo;
import com.lx.framework.demo1.user.servcie.UserInfoService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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

    public ConcurrentHashMap<Long,UserInfo> userInfoConcurrentHashMap = new ConcurrentHashMap<>();

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

    public static void reduce(int i){
        System.out.println("第i次递归："+i);
        i++;
        reduce(i);
    }

    @GetMapping("/testVm")
    public DemoVo testVm(){
        for (int i = 0; i < 500000; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(Long.valueOf(i));
            userInfo.setNickName("test");
            userInfo.setHeadImg("test");
            userInfo.setUserSex(1);
            userInfo.setIdCard("test");
            userInfo.setUserStatus(1);
            userInfo.setPlatformCode("test");
            userInfo.setCreateTime(System.currentTimeMillis());
            userInfo.setUpdateTime(System.currentTimeMillis());
            userInfo.setIdType(1);
            userInfo.setUserName("test");
            userInfoConcurrentHashMap.put(Long.valueOf(i),userInfo);
        }
        int i = 0;
//        reduce(i);
        DemoVo demoVo = new DemoVo();
        demoVo.setStr1("1");
        return demoVo;
    }

    @GetMapping("/testVm1")
    public void testVm1() throws InterruptedException {
        while (true){
//            Thread.sleep(100);
            System.out.println(System.currentTimeMillis());
        }
    }
}
