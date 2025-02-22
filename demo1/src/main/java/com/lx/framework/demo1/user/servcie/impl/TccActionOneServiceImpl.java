package com.lx.framework.demo1.user.servcie.impl;

import com.lx.framework.demo1.user.entity.UserInfo;
import com.lx.framework.demo1.user.servcie.TccActionOneService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TccActionOneServiceImpl implements TccActionOneService {

    @Override
    public boolean tryDemo(UserInfo userInfo, String demoParamOne, String demoParamTwo) {
        log.info("执行分支事物try方法，事物ID:{}",RootContext.getXID());
        try {
            log.info("模拟执try行业务逻辑");
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        log.info("执行分支事物commit方法，事物ID:{}",actionContext.getXid());
        // 因为接口的方法中使用了@BusinessActionContextParameter(paramName = "demoParamOne")，所以这里就能从actionContext中获取
        String demoParamOne = actionContext.getActionContext("demoParamOne").toString();
        String demoParamTwo = actionContext.getActionContext("demoParamTwo").toString();
        try {
            log.info("模拟执行commit业务逻辑");
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        log.info("执行分支事物rollback方法，事物ID:{}",actionContext.getXid());
        // 因为接口的方法中使用了@BusinessActionContextParameter(paramName = "demoParamOne")，所以这里就能从actionContext中获取
        String demoParamOne = actionContext.getActionContext("demoParamOne").toString();
        String demoParamTwo = actionContext.getActionContext("demoParamTwo").toString();
        try {
            log.info("模拟执行rollback业务逻辑");
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
