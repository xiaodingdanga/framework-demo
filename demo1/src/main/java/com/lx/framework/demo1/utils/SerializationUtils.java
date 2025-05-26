package com.lx.framework.demo1.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author xin.liu
 * @description TODO
 * @date 2025-05-12  17:48
 * @Version 1.0
 */
public class SerializationUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 序列化为 byte[]
    public static byte[] serialize(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(obj);
    }

    // 反序列化为对象
    public static <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            return objectMapper.readValue(data, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}