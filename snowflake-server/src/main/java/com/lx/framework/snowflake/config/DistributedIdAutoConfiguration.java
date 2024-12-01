package com.lx.framework.snowflake.config;

import com.lx.framework.snowflake.RedisWorkIdChoose;
import com.lx.framework.snowflake.RandomWorkIdChoose;
import com.lx.framework.tool.redis.ApplicationContextHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 分布式 ID 自动装配
 *
 */
@Import(ApplicationContextHolder.class)
@Configuration
public class DistributedIdAutoConfiguration {

    /**
     * 本地 Redis 构建雪花 WorkId 选择器
     */
    @Bean
    public RedisWorkIdChoose redisWorkIdChoose() {
        return new RedisWorkIdChoose();
    }

    /**
     * 随机数构建雪花 WorkId 选择器。如果项目未使用 Redis，使用该选择器
     */
    @Bean
    @ConditionalOnMissingBean(RedisWorkIdChoose.class)
    public RandomWorkIdChoose randomWorkIdChoose() {
        return new RandomWorkIdChoose();
    }
}
