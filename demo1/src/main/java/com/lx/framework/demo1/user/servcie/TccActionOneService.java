package com.lx.framework.demo1.user.servcie;

import com.lx.framework.demo1.user.entity.UserInfo;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * 通过 @LocalTCC 这个注解，RM 初始化的时候会向 TC 注册一个分支事务。
 */
@LocalTCC
public interface TccActionOneService {

    /**
     * TCC的try方法：保存订单信息，状态为支付中
     * 定义两阶段提交，在try阶段通过@TwoPhaseBusinessAction注解定义了分支事务的 resourceId，commit和 cancel 方法
     *  name = 该tcc的bean名称,全局唯一
     *  commitMethod = commit 为二阶段确认方法
     *  rollbackMethod = rollback 为二阶段取消方法
     *  BusinessActionContextParameter注解 传递参数到二阶段中，二阶段方法中通过paramName属性指定的key获取到值
     *  useTCCFence seata1.5.1的新特性，用于解决TCC幂等，悬挂，空回滚问题，需在服务端数据库中增加日志表tcc_fence_log
     */
    @TwoPhaseBusinessAction(name = "tryDemo", commitMethod = "commit", rollbackMethod = "rollback", useTCCFence = true)
    boolean tryDemo(UserInfo userInfo
            , @BusinessActionContextParameter(paramName = "demoParamOne") String demoParamOne
            , @BusinessActionContextParameter(paramName = "demoParamTwo") String demoParamTwo);

    /**
     * TCC的confirm方法：订单状态改为支付成功
     * 二阶段确认方法可以另命名，但要保证与commitMethod一致
     * context可以传递try方法的参数
     * @param actionContext
     * @return
     */
    boolean commit(BusinessActionContext actionContext);

    /**
     * TCC的cancel方法：订单状态改为支付失败
     * 二阶段取消方法可以另命名，但要保证与rollbackMethod一致
     * @param actionContext
     * @return
     */
    boolean rollback(BusinessActionContext actionContext);

}


