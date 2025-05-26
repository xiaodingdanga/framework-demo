//package com.lx.framework.demo1.user.servcie.impl;
//
//import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
//import com.lx.framework.demo1.user.entity.UserInfo;
//import com.lx.framework.demo1.user.mapper.UserInfoMapper;
//import com.lx.framework.demo1.user.servcie.UserInfoTccService;
//import io.seata.core.context.RootContext;
//import io.seata.rm.tcc.api.BusinessActionContext;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * 账户表
// *
// * @author xin.liu
// * @since 2024-03-10
// */
//@Component
//@Slf4j
//
//public class UserInfoServiceTccImpl implements UserInfoTccService {
//
//    @Autowired
//    UserInfoMapper userInfoMapper;
//
//    @Override
//    public UserInfo prepareSaveOrder(UserInfo userInfo, Long userId) {
////        log.info("执行分支事物1try:{}", RootContext.getXID());
//        new LambdaUpdateChainWrapper<>(userInfoMapper).eq(UserInfo::getUserId, 1L).set(UserInfo::getUserName, "perpare").update();
//        int insert = userInfoMapper.insert(userInfo);
//        log.info("模拟保存订单{}", insert > 0 ? "成功" : "失败");
//        return userInfo;
//    }
//
//
//    @Override
//    public boolean commit(BusinessActionContext actionContext) {
//        // 获取订单id
//        // 因为接口的方法中使用了@BusinessActionContextParameter(paramName = "orderId")，所以这里就能从actionContext中获取
//        log.info("执行分支事物1commit:{}", actionContext.getXid());
////        long userId = Long.parseLong(actionContext.getActionContext("id").toString());
//        //更新订单状态为支付成功
//        boolean update = new LambdaUpdateChainWrapper<>(userInfoMapper).eq(UserInfo::getUserId, 1L).set(UserInfo::getUserName, "commit").update();
////        UserInfo userInfo = new UserInfo();
////        userInfo.setUserId(1L);
////        userInfo.setUserName("test");
////        userInfo.setPlatformCode("test");
////        try {
////            int insert = userInfoMapper.insert(userInfo);
////        } catch (Exception e) {
////            e.printStackTrace();
////            return false;
////        }
////        log.info("更新订单id:{}", update ? "成功" : "失败");
//        return true;
//    }
//
//
//    @Override
//    public boolean rollback(BusinessActionContext actionContext) {
//        // 获取订单id
//        // 因为接口的方法中使用了@BusinessActionContextParameter(paramName = "orderId")，所以这里就能从actionContext中获取
//        log.info("执行分支事物1rollback:{}", actionContext.getXid());
////        long userId = Long.parseLong(actionContext.getActionContext("id").toString());
//        //更新订单状态为支付成功
//        boolean update = new LambdaUpdateChainWrapper<>(userInfoMapper).eq(UserInfo::getUserId, 1).set(UserInfo::getUserName, "rollback").update();
//        log.info("回滚订单id:{}", update ? "成功" : "失败");
//        return true;
//    }
//
//}
