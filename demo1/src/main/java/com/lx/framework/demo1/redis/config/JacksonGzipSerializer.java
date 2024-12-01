package com.lx.framework.demo1.redis.config;

import com.lx.framework.demo1.utils.GzipUtil;
import io.micrometer.common.lang.Nullable;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-11-22  17:37
 * @Version 1.0
 */
public class JacksonGzipSerializer extends GenericJackson2JsonRedisSerializer {
    @Override
    public byte[] serialize(@Nullable Object source) throws SerializationException {
        byte[] raw = super.serialize(source);
        try {
            return GzipUtil.compress(raw);
        } catch (IOException ioe) {
            throw new SerializationException("Exception", ioe);
        }
    }

    @Override
    public Object deserialize(@Nullable byte[] source) throws SerializationException {
        try {
            byte[] raw = GzipUtil.decompress(source);
            return deserialize(raw, Object.class);
        } catch (IOException ioe) {
            throw new SerializationException("Exception", ioe);
        }
    }
}