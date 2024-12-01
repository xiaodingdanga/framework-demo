package com.lx.framework.demo1.redis.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


/**
 * @author xin.liu
 * @description TODO
 * @date 2024-11-19  19:05
 * @Version 1.0
 */
@EnableCaching
@Configuration
public class CacheConfig {

    private static final CacheKeyPrefix DEFAULT_CACHE_KEY_PREFIX = cacheName -> cacheName+":";
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration config = defaultCacheConfig(7200);
        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        //方式一 设置默认的key过期时间
//        return new RedisConfigCacheManager(cacheWriter, config);
        //方式二 自定义key的过期时间
        return new RedisConfigCacheManager(cacheWriter, config,initCacheConfigMap());
    }

    private RedisCacheConfiguration defaultCacheConfig(Integer seconds){
        // 配置序列化（解决乱码的问题）
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(seconds)) // 设置缓存过期时间为seconds秒
                .computePrefixWith(DEFAULT_CACHE_KEY_PREFIX)  //修改key的入库格式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JacksonGzipSerializer()));
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    /**
     * 初始化多个缓存配置
     */
    private Map<String,RedisCacheConfiguration> initCacheConfigMap(){
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        //key为cacheNames或者value的值，会优先匹配map，然后才走default
        configMap.put("cacheNames",defaultCacheConfig(3600));
        return configMap;
    }

}