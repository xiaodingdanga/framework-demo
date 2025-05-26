package com.lx.framework.demo1.user1.controller;

import com.lx.framework.demo1.user.entity.UserInfo;
//import com.lx.framework.demo1.user.servcie.UserInfoTccService;
import com.lx.framework.demo1.user1.entity.User;
import com.lx.framework.demo1.user1.service.UserService;
import com.lx.framework.tool.startup.handler.customException.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotBlank;

import java.io.IOException;
import java.util.List;
/**
* @author xin.liu
* @since 2024-11-18
*/
@RestController

@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/tcc")
    public UserInfo tcc() throws InterruptedException {
        UserInfo userInfo = new UserInfo();
        userService.tcc();
        return userInfo;
    }

    @GetMapping("/selectOne")
    public User getUser(@RequestParam("id") Integer id){
        User userOne = userService.getUser(id);
        return userOne;
    }

    @GetMapping("/listAll")
//    @Transactional(readOnly = true)
    @Transactional(timeout = 2)
    public List<User> getAllUser(){
        List<User> userList = userService.getAllUser();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new BusinessException();
        }
        User user = new User();
        user.setName("haha");
        user.setSex(1);
        userService.add(user);

        return userList;
    }

    @PostMapping("/add")
    @Transactional(propagation = Propagation.REQUIRED)
    public Object add(@Validated @RequestBody User user) throws IOException {
        userService.add(user);
        return null;
    }

    @PutMapping("/update")
    public int update(@Validated @RequestBody User user) {
        int num = userService.modify(user);
        return num;
    }

    @DeleteMapping(value = "/delete/{ids}")
    public Object remove(@NotBlank(message = "{required}") @PathVariable String ids) {
        userService.remove(ids);
        return null;
    }

    @PostMapping(value = "/saveUserWithBatch")
    public Object saveUserWithBatch() {
        userService.saveUserWithBatch();
        return null;
    }

    @PostMapping(value = "/saveUserWithThread")
    public Object saveUserWithThread() {
        userService.saveUserWithThread();
        return null;
    }
}
