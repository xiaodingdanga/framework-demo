package com.lx.framework.demo1.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author xin.liu
 * @description 为了测试/test1加密使用的配置，其余情况使用底层config
 * @date 2025-05-12  15:20
 * @Version 1.0
 */
@Configuration
public class RedisJdkConfig {

    /*
     * 定义多个RedisTemplate，分别对应不同的序列化方式
     * 在业务代码中通过 @Qualifier 注入指定的 RedisTemplate：@Qualifier("jsonRedisTemplate")
     */
    @Bean
    public RedisTemplate<String, Object> jdkRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new JdkSerializationRedisSerializer());
        return template;
    }
}