package com.lx.framework.demo1.user.controller;

import com.lx.framework.demo1.fegin.entity.SysUserEntity;
import com.lx.framework.demo1.fegin.servcie.UserFeignClient;
import com.lx.framework.demo1.user.entity.UserInfo;
import com.lx.framework.demo1.user.pojo.vo.DemoVo;
import com.lx.framework.demo1.user.servcie.UserInfoService;
import com.lx.framework.demo1.utils.SM4UtilsPrivate;
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

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private SM4UtilsPrivate sm4UtilsPrivate;

    public ConcurrentHashMap<Long,UserInfo> userInfoConcurrentHashMap = new ConcurrentHashMap<>();

    @GetMapping(value = "/AT")
//    @GlobalTransactional
    public UserInfo AT() throws InterruptedException {
        UserInfo userInfo = new UserInfo();
        //第一个分支事物
//        try{
            userInfoService.test();
//        } catch (FeignException e) {
//            // 记录日志并处理异常
//            System.out.println("进入异常1");
//            throw new RuntimeException("Remote service call failed", e);
//        }
        //第二个分支事物
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId("044d1a8a68084e979dccddc812b03201");
//        try {
            SysUserEntity reduct = userFeignClient.reduct(sysUserEntity);
//        }catch (Exception e) {
//            // 记录日志并处理异常
//            System.out.println("进入异常2");
//            throw new RuntimeException("Remote service call failed", e);
//        }
//        if (null == reduct){
//            // 记录日志并处理异常
//            System.out.println("进入异常2");
//            throw new RuntimeException("Remote service call failed");
//        }
        return userInfo;
    }



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
    public void testVm1() {
        String s = sm4UtilsPrivate.enCode("123");
        System.out.println(s);
        System.out.println(sm4UtilsPrivate.deCode(s));
    }
}
