package com.lx.framework.demo1.redis.controller;

import com.alibaba.fastjson2.JSONObject;
import com.lx.framework.demo1.redis.entity.Test;
import com.lx.framework.demo1.redis.mapper.TestMapper;
import com.lx.framework.tool.startup.utils.Lock;
import com.lx.framework.tool.startup.utils.RedisLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.connection.RedisListCommands;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author xin.liu
 * @description redis基于lettuce实现redis分布式锁
 * @date 2024-02-28  14:03
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/redis")
public class RedisController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ReactiveRedisTemplate reactiveRedisTemplate;

    @Autowired
    private RedisLockUtil redisLockUtil;

    @Autowired
    private TestMapper testMapper;

    @GetMapping("/set")
    public String set(String key) {

//        RedisUtil.lock()
//        redisTemplate.opsForValue().set("key","value");


        return  stringRedisTemplate.opsForValue().setIfAbsent(key, "123", 60000l, TimeUnit.SECONDS).toString();
    }

    @GetMapping("/get")
    public String get(String key) {

//        RedisUtil.lock()
//        redisTemplate.opsForValue().set("key","value");
        String s = stringRedisTemplate.opsForValue().get(key);
        return s;
    }

    @GetMapping("/lock")
    public String lock() throws InterruptedException {
        Lock lock = redisLockUtil.lock("key", "value", 5);
        Test test1 = testMapper.selectById(137);
        int i = test1.getCount()+1;
        test1.setCount(i);
        Thread.sleep(500);
        testMapper.updateById(test1);
        redisLockUtil.unlock(lock);
        System.out.println(Thread.currentThread().getName()+"执行成功！");
        return "ok";
    }

    @Cacheable(cacheNames = "dict-value" ,key = "#p0 + '_' + #p0")
    @PostMapping("/testCaceh")
    public JSONObject testCaceh(@RequestParam(value = "type") Integer type, @RequestParam(value = "parentId") Integer parentId) {
        String str  = "{\"responseCode\":100000000,\"responseMessage\":\"查询成功\",\"responseData\":[{\"id\":42061,\"name\":\"陕西省\",\"children\":null},{\"id\":28625,\"name\":\"广东省\",\"children\":null},{\"id\":30494,\"name\":\"广西壮族自治区\",\"children\":null},{\"id\":31925,\"name\":\"海南省\",\"children\":null},{\"id\":32175,\"name\":\"重庆市\",\"children\":null},{\"id\":33248,\"name\":\"四川省\",\"children\":null},{\"id\":38101,\"name\":\"贵州省\",\"children\":null},{\"id\":39685,\"name\":\"云南省\",\"children\":null},{\"id\":41272,\"name\":\"西藏自治区\",\"children\":null},{\"id\":26456,\"name\":\"湖南省\",\"children\":null},{\"id\":43500,\"name\":\"甘肃省\",\"children\":null},{\"id\":45050,\"name\":\"青海省\",\"children\":null},{\"id\":45536,\"name\":\"宁夏回族自治区\",\"children\":null},{\"id\":45834,\"name\":\"新疆维吾尔自治区\",\"children\":null},{\"id\":94766,\"name\":\"河北保定\",\"children\":null},{\"id\":317148,\"name\":\"台湾省\",\"children\":null},{\"id\":317149,\"name\":\"香港特别行政区\",\"children\":null},{\"id\":317150,\"name\":\"澳门特别行政区\",\"children\":null},{\"id\":11953,\"name\":\"江苏省\",\"children\":null},{\"id\":812,\"name\":\"天津市\",\"children\":null},{\"id\":1135,\"name\":\"河北省\",\"children\":null},{\"id\":3697,\"name\":\"山西省\",\"children\":null},{\"id\":5327,\"name\":\"内蒙古自治区\",\"children\":null},{\"id\":6726,\"name\":\"辽宁省\",\"children\":null},{\"id\":8445,\"name\":\"吉林省\",\"children\":null},{\"id\":9570,\"name\":\"黑龙江省\",\"children\":null},{\"id\":11701,\"name\":\"上海市\",\"children\":null},{\"id\":458,\"name\":\"北京市\",\"children\":null},{\"id\":13627,\"name\":\"浙江省\",\"children\":null},{\"id\":15140,\"name\":\"安徽省\",\"children\":null},{\"id\":16938,\"name\":\"福建省\",\"children\":null},{\"id\":18227,\"name\":\"江西省\",\"children\":null},{\"id\":20133,\"name\":\"山东省\",\"children\":null},{\"id\":22176,\"name\":\"河南省\",\"children\":null},{\"id\":24954,\"name\":\"湖北省\",\"children\":null}],\"requestId\":\"4b3a51f2-3b35-4bd4-8fab-536ed16bc7b5\"}";
        JSONObject jsonObject1 = JSONObject.parseObject(str);
        return jsonObject1;
    }

    @GetMapping("/api")
    public String api() {
        //string
        ValueOperations valueOperations = redisTemplate.opsForValue();
//        valueOperations.set("int", 1);
//        valueOperations.set("string", "string");
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("string");
//        strings.add("int");
        //批量获取值
//        List list = valueOperations.multiGet(strings);
        //从指定位置获取值
//        String s = valueOperations.get("string", 0, 1);
        //批量设置值
//        HashMap<String, String> stringStringHashMap = new HashMap<>();
//        stringStringHashMap.put("ma", "ma");
//        stringStringHashMap.put("mb", "mb");
//        valueOperations.multiSet(stringStringHashMap);
        //在指定的偏移量位置设置字符串值的一部分
//        valueOperations.set("string", "string",2);
        //在末尾追加字符串
//        valueOperations.append("string","1");
        //如果键存在则返回false，否则返回true
//        Boolean b = valueOperations.setIfAbsent("string1", "string");
        //如果键存在并且成功设置了新值，则返回 true；如果键不存在，则返回 false
//        Boolean b = valueOperations.setIfPresent("string2", "string23423");
        //位操作
//        Boolean b = valueOperations.setBit("bit", 1, true);
//        System.out.println("-----------------:"+b);
//        Boolean bit = valueOperations.getBit("bit", 0);
//        System.out.println("-----------------:"+bit);
        //减一
//        valueOperations.decrement("int");
        //加一
//        valueOperations.increment("int");
//
//        Long string = valueOperations.size("string");
//        System.out.println(string);

        //list
        ListOperations listOperations = redisTemplate.opsForList();

//        listOperations.leftPush("list", "string7");
//        listOperations.set("list", 1, "string1");
//        Object list = listOperations.index("list", 1);
//        System.out.println(list.toString());
//        Object list = listOperations.leftPop("list");
//        System.out.println(list.toString());
//        Object list1 = listOperations.rightPop("list");
//        System.out.println(list1.toString());
//        Long l = listOperations.indexOf("list", "string");
//        System.out.println(l);
//        List list = listOperations.range("list", 0, 3);
//        System.out.println(list);
//        listOperations.rightPopAndLeftPush("list", "list1");
//        listOperations.remove("list", 1, "string7");
//        listOperations.move( "list", RedisListCo mmands.Direction.first(), "list1", RedisListCommands.Direction.last());

        //set
        SetOperations setOperations = redisTemplate.opsForSet();
//        setOperations.add("set", "string1", "string2", "string3", "string4", "string5", "string6", "string7");
//        setOperations.add("set1", "string2");
//        Set difference = setOperations.difference("set", "set1");
//        System.out.println(difference);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("set");
        strings.add("set1");
        Set difference1 = setOperations.difference(strings);
        System.out.println(difference1);


//        //zset
//        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

//        //hash
//        HashOperations hashOperations = redisTemplate.opsForHash();

        return "success";
    }


}