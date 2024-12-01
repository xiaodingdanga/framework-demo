package com.lx.framework.demo1.redis.controller;

import com.alibaba.fastjson2.JSONObject;
import com.lx.framework.demo1.redis.entity.Test;
import com.lx.framework.demo1.redis.mapper.TestMapper;
import com.lx.framework.tool.startup.utils.Lock;
import com.lx.framework.tool.startup.utils.RedisLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

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
    public String set() {

//        RedisUtil.lock()
//        redisTemplate.opsForValue().set("key","value");


        return  stringRedisTemplate.opsForValue().setIfAbsent("key", "123", 60l, TimeUnit.SECONDS).toString();
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

}