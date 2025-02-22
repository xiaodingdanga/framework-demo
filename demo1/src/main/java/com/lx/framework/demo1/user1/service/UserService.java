package com.lx.framework.demo1.user1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.framework.demo1.user1.entity.User;

import java.util.List;

/**
* @author xin.liu
* @since 2024-11-18
*/

public interface UserService extends IService<User> {

    /**
    * User
    * @param
    * @return
    */
    User getUser(Integer id);

    /**
    * User
    * @param
    * @return
    */
    List<User> getAllUser();

    /**
    * User
    * @param user
    * @return
    */
    void add(User user);

    /**
    * User
    * @param user
    * @return
    */
    int modify(User user);

    /**
    * User
    * @param ids
    * @return
    */
    void remove(String ids);

    void saveUserWithBatch();

    void saveUserWithThread();

    void tcc() throws InterruptedException;
}


