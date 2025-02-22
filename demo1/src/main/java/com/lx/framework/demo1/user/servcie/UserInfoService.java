package com.lx.framework.demo1.user.servcie;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.framework.demo1.user.entity.UserInfo;
import com.lx.framework.demo1.user1.entity.User;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

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

    void add1(UserInfo userInfo);

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

    void test() throws InterruptedException;

}


