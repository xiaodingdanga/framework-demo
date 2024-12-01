package com.lx.framework.demo1.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description 雪花算法id
 * @return: null
 * @author xin.liu
 * @date  10:28
 */
@Component
public class SnowflakeUtils implements InitializingBean {

    @Value("${snowflake.workId:31}")
    private Integer workId;

    @Value("${snowflake.datacenterId:31}")
    private Integer datacenterId;

    private Snowflake snowflake;

    public long getId() {
        return snowflake.nextId();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        snowflake = IdUtil.getSnowflake(workId, datacenterId);
    }

}
