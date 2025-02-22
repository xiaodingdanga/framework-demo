package com.lx.framework.demo1.user.servcie.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lx.framework.demo1.fegin.entity.SysUserEntity;
import com.lx.framework.demo1.fegin.servcie.UserFeignClient;
import com.lx.framework.demo1.user.entity.UserInfo;
import com.lx.framework.demo1.user.mapper.UserInfoMapper;
import com.lx.framework.demo1.user.servcie.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.framework.demo1.user1.entity.User;
import feign.FeignException;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.spring.annotation.GlobalLock;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
/**
* 账户表
*
* @author xin.liu
* @since 2024-03-10
*/
@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfo(Integer id){
        return userInfoMapper.selectById(id);
    }

    @Override
    public List<UserInfo> getAllUserInfo(){
//        return userInfoMapper.selectList(null);
        PageHelper.startPage(1, 10);
        Page<UserInfo> page =(Page<UserInfo>) userInfoMapper.selectAll();
        return userInfoMapper.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void add(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);

    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void add1(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
        int i = 1/0;
    }

    @Override
    public int modify(UserInfo userInfo) {
        return  userInfoMapper.updateById(userInfo);
    }

    @Override
    public void remove(String ids) {
        if(StringUtils.isNotEmpty(ids)){
            String[] array = ids.split(",");
            if (!CollectionUtils.isEmpty(Arrays.asList(array))) {
                userInfoMapper.deleteBatchIds(Arrays.asList(array));
            }
        }
    }

    @Override
    public void insert1() {
//        userInfoMapper.insert1("15845256867",1L);
//        ArrayList<UserInfo> userInfos = new ArrayList<>();
//        long l = System.currentTimeMillis();
//        for (int j = 1; j < 60; j++) {
//            UserInfo userInfo;
//            String s;
//            for (int i = 0; i < 5000; i++) {
//                s = IdUtil.fastSimpleUUID();
//                userInfo = new UserInfo();
//                userInfo.setUserId(1l);
//                userInfo.setUserName(s);
//                userInfo.setNickName(s);
//                userInfo.setIdType(1);
//                userInfo.setUserSex(1);
//                userInfo.setHeadImg(s);
//                userInfo.setIdCard(s);
//                userInfo.setUserStatus(1);
//                userInfo.setPlatformCode("ssc");
//                userInfos.add(userInfo);
//            }
//            userInfoMapper.insert2(userInfos);
//            System.out.println("执行第"+j+"次");
//        }
//        long l1 = System.currentTimeMillis();
//        System.out.println("用时："+ (l1 - l) +"毫秒");
        System.out.println(this.getBaseMapper().selectAll().size());
    }

    public static void main(String[] args) {
        System.out.println(IdUtil.fastSimpleUUID());
    }

    @Override
    @Transactional
    public void test() throws InterruptedException {
        System.out.println("开始事物："+ RootContext.getXID());

        new LambdaUpdateChainWrapper<>(userInfoMapper).eq(UserInfo::getUserId, 1).set(UserInfo::getUserName,"perpare1").update();

//        Thread.sleep(10000);
        //异常
//        int a = 1/0;
    }
}
