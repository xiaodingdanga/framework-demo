package com.lx.framework.demo1.mq.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author xin.liu
 * @description mq消息队列保证消息不丢失
 * @date 2024-02-28  14:03
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/mq")
public class MqController {
    @Resource
    RabbitTemplate rabbitTemplate;

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "Test message, hello world!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        JSONObject json = new JSONObject();
        json.put("messageId", messageId);
        json.put("messageData", messageData);
        json.put("createTime", createTime);
//        rabbitTemplate.convertAndSend("directExchange", "directRouting", json);
        sendMsgToMq("directExchange","directRouting",json);
        return "ok";
    }

    @GetMapping("/sendTopicFirstMessage")
    public String sendTopicFirstMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: first";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        JSONObject json = new JSONObject();
        Snowflake snowflake = IdUtil.getSnowflake();
        json.put("messageId", snowflake.nextId());
        json.put("messageData", messageData);
        json.put("createTime", createTime);
        //设置不存在的路由键，就会触发ReturnCallBack
//        rabbitTemplate.convertAndSend("topicExchange","topic.123a",json);
        //设置不存在的交换机名称，就会触发confirm
//        rabbitTemplate.convertAndSend("topicExchange12321","topic.first",json);
//        rabbitTemplate.convertAndSend("topicExchange","topic.first",json);
        sendMsgToMq("topicExchange","topic.first",json);
        //todo 将消息保存到消息表
        return "ok";
    }

    @GetMapping("/sendTopicSecondMessage")
    public String sendTopicSecondMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: second";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        JSONObject json = new JSONObject();
        json.put("messageId", messageId);
        json.put("messageData", messageData);
        json.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.second", json);
        return "ok";
    }

    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: all";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        JSONObject json = new JSONObject();
        json.put("messageId", messageId);
        json.put("messageData", messageData);
        json.put("createTime", createTime);
        rabbitTemplate.convertAndSend("fanoutExchange", "fanout.send", json);
        return "ok";
    }

    /**
     * 发送消息并
     * 添加 CorrelationData数据
     * @param exchange
     * @param routingKey
     * @param json
     */
    public void sendMsgToMq(String exchange, String routingKey, JSONObject json){
        CorrelationData correlationData = new CorrelationData();
        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setExpiration("1000"); // 设置过期时间，单位：毫秒
        //设置消息持久化
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        Message message = new Message(json.toJSONString().getBytes(),messageProperties);
        correlationData.setReturned(new ReturnedMessage(message,1,"1",exchange,routingKey));
        rabbitTemplate.convertAndSend(exchange,routingKey,message,correlationData);
    }

    public static void main(String[] args) {
        System.out.println(31<<17);
    }
}