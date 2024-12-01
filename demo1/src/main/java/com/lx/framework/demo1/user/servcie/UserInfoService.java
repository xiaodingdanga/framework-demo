package com.lx.framework.demo1.user.servcie;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.framework.demo1.user.entity.UserInfo;

import java.util.List;

/**
* @author xin.liu
* @since 2024-03-10
*/
public interface UserInfoService extends IService<UserInfo> {

    /**
    * UserInfo
    * @param
    * @return
    */
    UserInfo getUserInfo(Integer id);

    /**
    * UserInfo
    * @param
    * @return
    */
    List<UserInfo> getAllUserInfo();

    /**
    * UserInfo
    * @param userInfo
    * @return
    */
    void add(UserInfo userInfo);

    /**
    * UserInfo
    * @param userInfo
    * @return
    */
    int modify(UserInfo userInfo);

    /**
    * UserInfo
    * @param ids
    * @return
    */
    void remove(String ids);

    void insert1();
}


