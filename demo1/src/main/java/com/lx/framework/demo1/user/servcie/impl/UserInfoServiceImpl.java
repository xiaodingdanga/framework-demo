package com.lx.framework.demo1.user.servcie.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lx.framework.demo1.user.entity.UserInfo;
import com.lx.framework.demo1.user.mapper.UserInfoMapper;
import com.lx.framework.demo1.user.servcie.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

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
    public void add(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
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

}
