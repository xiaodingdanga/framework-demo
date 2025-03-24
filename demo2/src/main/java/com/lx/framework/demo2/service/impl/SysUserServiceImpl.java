package com.lx.framework.demo2.service.impl;

import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.framework.demo2.dao.entity.SysUserEntity;
import com.lx.framework.demo2.dao.mapper.SysUserMapper;
import com.lx.framework.demo2.service.SysUserService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lx
 * @since 2023-08-14
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUserEntity getUserInfoById(@RequestBody String userId) {
        return sysUserMapper.selectById(userId);
    }

    @Override
//    @Transactional
    public void reduct(String id) {
        System.out.println("更新sort数据:" + RootContext.getXID());
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setId("044d1a8a68084e979dccddc812b03200");
        sysUserEntity.setName("test");
        sysUserMapper.insert(sysUserEntity);
//        new LambdaUpdateChainWrapper<>(sysUserMapper).eq(SysUserEntity::getId, id).set(SysUserEntity::getSort, 1).update();
    }

    @Override
//    @Transactional
    public Boolean reduct1() throws Exception {
        //模拟冻结库存
        log.info("执行分支事物2try:{}", RootContext.getXID());
        try {
//            SysUserEntity sysUserEntity = new SysUserEntity();
//            sysUserEntity.setId("044d1a8a68084e979dccddc812b03200");
//            sysUserEntity.setName("test");
//            sysUserMapper.insert(sysUserEntity);
            new LambdaUpdateChainWrapper<>(sysUserMapper).eq(SysUserEntity::getId, "044d1a8a68084e979dccddc812b03200").set(SysUserEntity::getSort, 2).update();
        } catch (Exception e) {
            return false;
        }
//        if (true){
//            throw new RuntimeException("异常");
//        }
        return true;
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        //模拟使用库存
        log.info("执行分支事物2commit:{}", actionContext.getXid());
        new LambdaUpdateChainWrapper<>(sysUserMapper).eq(SysUserEntity::getId, "044d1a8a68084e979dccddc812b03200").set(SysUserEntity::getSort, 3).update();
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        //模拟回滚库存
        log.info("执行分支事物2rollback:{}", actionContext.getXid());
        new LambdaUpdateChainWrapper<>(sysUserMapper).eq(SysUserEntity::getId, "044d1a8a68084e979dccddc812b03200").set(SysUserEntity::getSort, 1).update();
        return true;
    }

    @Transactional
    public void update(String id) {
        new LambdaUpdateChainWrapper<>(sysUserMapper).eq(SysUserEntity::getId, id).set(SysUserEntity::getSort, 1).update();
        int i = 1 / 0;
    }

}
