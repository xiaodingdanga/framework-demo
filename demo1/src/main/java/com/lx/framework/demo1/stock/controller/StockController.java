package com.lx.framework.demo1.stock.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author xin.liu
 * @description 模拟redis+lua异步扣减库存
 * @date 2024-02-28  14:03
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/stock")
public class StockController {
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/initStock")
    public String initStock() {
        //有多种方式模拟预热商品库存，定时任务预热、人工预热、项目启动预热等均可。
        redisTemplate.opsForValue().set("stock_key_1", 100);
        return "预热成功";
    }

    @RequestMapping("/order")
    public String order() {
        //lua脚本判断流程
        // 当用户已经在短时间下单了商品  返回-1 表示重复下单
        // 当库存小于扣减数量  返回0 表示库存不足
        // 否则返回1 扣减库存并且在用户列表添加用户表示表示已经下单 使用DECRBY防止超卖
        //-- 参数说明
        //-- KEYS[1]: 商品库存键
        //-- KEYS[2]: 用户下单键
        //-- ARGV[1]: 扣减数量
        String lua =
                "local stock_key = KEYS[1] " +
                "local user_key = KEYS[2] " +
                "local product_number = tonumber(ARGV[1]) " +
                "local stock = tonumber(redis.call('GET', stock_key)) " +
                "local user_order_key = tonumber(redis.call('GET', user_key)) " +
                "if user_order_key ~= nil then " +
                "return -1 " +
                "end " +
                "if stock == nil or stock <= product_number then " +
                "return 0 " +
                "end " +
                "redis.call('DECRBY', stock_key, product_number) " +
                "redis.call('SET', user_key, 1) " +
                "redis.call('EXPIRE', user_key, 60)" +
                "return 1 ";
        String productId = "1";
        String userId = "123";
        ArrayList<String> keys = new ArrayList<>();
        keys.add("stock_key_"+productId);
        keys.add(productId + ":" + userId);
        Object execute = redisTemplate.execute(new DefaultRedisScript<>(lua, Integer.class), keys ,10);
        if(Integer.valueOf(execute.toString()) == -1){
            return "重复下单";
        }
        if(Integer.valueOf(execute.toString()) == 0){
            return "库存不足";
        }
        if(Integer.valueOf(execute.toString()) == 1){
            //异步执行创建订单扣减库存操作
            System.out.println("异步执行创建订单扣减库存操作");
        }
        return  "下单成功";
    }

}