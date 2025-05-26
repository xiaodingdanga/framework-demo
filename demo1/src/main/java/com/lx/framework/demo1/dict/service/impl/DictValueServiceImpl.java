package com.lx.framework.demo1.dict.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;
import com.lx.framework.demo1.dict.entity.DictValue;
import com.lx.framework.demo1.dict.mapper.DictValueMapper;
import com.lx.framework.demo1.dict.service.IDictValueService;
import com.lx.framework.demo1.utils.CompressionUtils;
import com.lx.framework.demo1.utils.SerializationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author lx
 * @since 2024-09-19
 */
@Service
@Slf4j
public class DictValueServiceImpl extends ServiceImpl<DictValueMapper, DictValue> implements IDictValueService {

    @Autowired
    @Qualifier("jdkRedisTemplate")
    private RedisTemplate<String, Object> jdkRedisTemplate;

    private static final String DICT_VALUE_KEY = "DICT:VALUE:KEY:";
    private static final String DICT_VALUE_KEY_BATCH = "DICT:VALUE:KEY:BATCH:";

    @Override
    public Map<String, DictValue> getDictMapByType(Integer type) {
        return this.lambdaQuery().eq(DictValue::getType, type).eq(DictValue::getIsDeleted, 1).list().stream().collect(Collectors.toMap(DictValue::getCode, DictValue -> DictValue));
    }

    @Override
    public Map<String, DictValue> getDictMapByTypeV1(Integer type) {
        Map<String, String> entries;
        byte[] compressedData = (byte[]) jdkRedisTemplate.opsForValue().get(DICT_VALUE_KEY + type);
        if (compressedData != null) {
            try {
                byte[] decompressed = CompressionUtils.decompress(compressedData);
                entries = SerializationUtils.deserialize(decompressed, Map.class);
                if (CollectionUtil.isNotEmpty(entries)) {
                    return entries.values().stream().map(value -> JSONObject.parseObject(value, DictValue.class)).collect(Collectors.toMap(DictValue::getCode, DictValue -> DictValue));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        entries = this.lambdaQuery().eq(DictValue::getType, type).eq(DictValue::getIsDeleted, 1).list().stream().collect(Collectors.toMap(DictValue::getCode, DictValue -> {
            DictValue.setId(null);
            DictValue.setDescription(null);
            DictValue.setSort(null);
            DictValue.setIsDeleted(null);
            DictValue.setCreateBy(null);
            DictValue.setUpdateBy(null);
            DictValue.setCreateTime(null);
            DictValue.setUpdateTime(null);
            DictValue.setParentId(null);
            DictValue.setType(null);
            return JSONObject.toJSONString(DictValue);
        }));

        byte[] serialized;
        try {
            serialized = SerializationUtils.serialize(entries);
            byte[] compressed = CompressionUtils.compress(serialized);
            jdkRedisTemplate.opsForValue().set(DICT_VALUE_KEY + type, compressed);
            jdkRedisTemplate.expire(DICT_VALUE_KEY + type, 2, TimeUnit.HOURS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entries.values().stream().map(value -> JSONObject.parseObject(value, DictValue.class)).collect(Collectors.toMap(DictValue::getCode, DictValue -> DictValue));
    }

    @Override
    public Map<String, DictValue> getDictMapByTypeV2(Integer type) {
        String pattern = DICT_VALUE_KEY_BATCH + type + ":*";
        Map<String, DictValue> valuesByPrefix = getValuesByPrefix(pattern);
        if (CollectionUtil.isNotEmpty(valuesByPrefix)) {
            return valuesByPrefix;
        }
        List<DictValue> list = this.lambdaQuery().eq(DictValue::getType, type).eq(DictValue::getIsDeleted, 1).list();

        List<List<DictValue>> batches = CollectionUtil.splitList(list, 1000);
        HashMap<String, DictValue> stringDictValueHashMap = new HashMap<>();
        for (int i = 0; i < batches.size(); i++) {
            Map<String, DictValue> collect = batches.get(i).stream().collect(Collectors.toMap(DictValue::getCode, DictValue -> {
                DictValue dictValue = new DictValue();
                dictValue.setName(DictValue.getName());
                dictValue.setCode(DictValue.getCode());
                return dictValue;
            }));
            stringDictValueHashMap.putAll(collect);
            System.out.println("第" + i + "批");
            jdkRedisTemplate.opsForValue().set(DICT_VALUE_KEY_BATCH + type + ":" + i, collect);
            jdkRedisTemplate.expire(DICT_VALUE_KEY_BATCH + type + ":" + i, 2, TimeUnit.HOURS);
        }
        return stringDictValueHashMap;
    }

    @Override
    public Map<String, DictValue> getDictMapByTypeV3(Integer type) {
        Map<String, DictValue> stringDictValueMap = (Map<String, DictValue>) jdkRedisTemplate.opsForValue().get(DICT_VALUE_KEY + type);
        if (CollectionUtil.isNotEmpty(stringDictValueMap)) {
            return stringDictValueMap;
        }
        Map<String, DictValue> collect = this.lambdaQuery().eq(DictValue::getType, type).eq(DictValue::getIsDeleted, 1).list().stream().collect(Collectors.toMap(DictValue::getCode, DictValue -> {
            //将DictValue除name和code外都set为null
            DictValue.setId(null);
            DictValue.setDescription(null);
            DictValue.setSort(null);
            DictValue.setIsDeleted(null);
            DictValue.setCreateBy(null);
            DictValue.setUpdateBy(null);
            DictValue.setCreateTime(null);
            DictValue.setUpdateTime(null);
            DictValue.setParentId(null);
            DictValue.setType(null);
            return DictValue;
        }));
        jdkRedisTemplate.opsForValue().set(DICT_VALUE_KEY + type, collect);
        jdkRedisTemplate.expire(DICT_VALUE_KEY + type, 2, TimeUnit.HOURS);
        return collect;
    }

    /**
     * 根据前缀 pattern 获取所有匹配的 key 对应的 value
     */
    public Map<String, DictValue> getValuesByPrefix(String pattern) {
        ScanOptions options = ScanOptions.scanOptions().match(pattern).build();
        Cursor<byte[]> cursor = jdkRedisTemplate.getConnectionFactory().getConnection().scan(options);

        Map<String, DictValue> result = new HashMap<>(30000);
        while (cursor.hasNext()) {
            String key = new String(cursor.next());
            Object value = jdkRedisTemplate.opsForValue().get(key);
            result.putAll((Map<String, DictValue>) value);
        }
        return result;
    }


}
