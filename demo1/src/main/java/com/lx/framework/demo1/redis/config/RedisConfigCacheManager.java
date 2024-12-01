package com.lx.framework.demo1.redis.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;
import java.util.Map;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-11-19  19:33
 * @Version 1.0
 */
public class RedisConfigCacheManager extends RedisCacheManager {
    public RedisConfigCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    public RedisConfigCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
    }

    @Override
    protected RedisCache createRedisCache(String cacheName, RedisCacheConfiguration cacheConfig) {
        final int lastIndexOf = StringUtils.lastIndexOf(cacheName, '#');
        //处理#自定义过期时间的情况
        if (lastIndexOf > -1) {
            String ttl = StringUtils.substring(cacheName, lastIndexOf + 1);
            cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(Long.parseLong(ttl)));
            cacheName = StringUtils.substring(cacheName, 0, lastIndexOf);
            return super.createRedisCache(cacheName, cacheConfig);
        }else{
            return super.createRedisCache(cacheName, cacheConfig);
        }
    }

}