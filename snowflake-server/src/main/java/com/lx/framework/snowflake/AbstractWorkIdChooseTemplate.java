package com.lx.framework.snowflake;

import cn.hutool.core.date.SystemClock;
import com.lx.framework.snowflake.entity.WorkCenterInfo;
import com.lx.framework.snowflake.utils.SnowflakeIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * 雪花算法模板生成
 *
 */
@Slf4j
public abstract class AbstractWorkIdChooseTemplate {

    /**
     * 是否使用 {@link SystemClock} 获取当前时间戳
     */
    @Value("${snowflake.is-use-system-clock:false}")
    private boolean isUseSystemClock;

    /**
     * 根据自定义策略获取 WorkId 生成器
     *
     * @return
     */
    protected abstract WorkCenterInfo chooseWorkId();

    /**
     * 选择 WorkId 并初始化雪花
     */
    public void chooseAndInit() {
        // 模板方法模式: 通过调用抽象方法获取 WorkId 来创建雪花算法，抽象方法的具体实现交给子类
        WorkCenterInfo workCenterInfo = chooseWorkId();
        long workId = workCenterInfo.getWorkId();
        long dataCenterId = workCenterInfo.getDataCenterId();
        // 生成机器标识之后，初始化工具类的雪花算法静态对象
        Snowflake snowflake = new Snowflake(workId, dataCenterId, isUseSystemClock);
        log.info("Snowflake type: {}, workId: {}, dataCenterId: {}", this.getClass().getSimpleName(), workId, dataCenterId);
        SnowflakeIdUtil.initSnowflake(snowflake);
    }
}
