package com.lx.framework.demo1.user1.controller;

import com.lx.framework.demo1.user1.entity.User;
import com.lx.framework.demo1.user1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotBlank;
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

    @GetMapping("/selectOne")
    public User getUser(@RequestParam("id") Integer id){
        User userOne = userService.getUser(id);
        return userOne;
    }

    @GetMapping("/listAll")
    public List<User> getAllUser(){
        List<User> userList = userService.getAllUser();
        return userList;
    }

    @PostMapping("/add")
    public Object add(@Validated @RequestBody User user) {
        userService.add( user);
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
