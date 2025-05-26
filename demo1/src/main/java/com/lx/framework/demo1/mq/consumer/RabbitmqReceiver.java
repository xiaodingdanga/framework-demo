package com.lx.framework.demo1.mq.consumer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-03-17  11:39
 * @Version 1.0
 */
@Slf4j
@Component
public class RabbitmqReceiver {


    /**
     * 消息最大重试次数
     */
    private static final int MAX_RETRIES = 3;

    /**
     * 重试间隔(秒)
     */
    private static final long RETRY_INTERVAL = 1;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 消费者监听，绑定交换机、队列、路由键
     */
    @RabbitListener(bindings = {@QueueBinding(exchange = @Exchange(value = "directExchange"),value = @Queue(value = "directQueue"),key = "directRouting")},containerFactory = "singleMessageListenerContainerFactory")
    public void receiveDirectMsg(JSONObject json, Message message, Channel channel) throws IOException {
        // 模拟处理时间
        try {
            Thread.sleep(2000); // 模拟处理消息需要2秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(LocalDateTime.now());
        //接收消息message
        log.info("Direct模式消费者收到消息: " + message);
    }

    /**
     * 消费者监听，绑定队列
     * 通过唯一标识存入redis用来判断重复消息，防止重复消费
     * 手动ack处理防止消费者端消息丢失
     */
    @RabbitListener(queues = "topicFirstQueue")
    public void receiveTopicFirstMsg(JSONObject json, Message message, Channel channel) throws IOException, InterruptedException {
        //接收消息message
        log.info("Topic模式(topicFirstQueue)消费者收到消息: " + json.toJSONString());
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String messageId = json.get("messageId").toString();
        log.info("【" + messageId + "】-正在处理的消息");
        //查看消息是否已消费

//        if (null != redisTemplate.opsForValue().get("rabbit-mq-"+messageId)) {
//            //手动确认消息已消费
//            channel.basicAck(deliveryTag, false);
//            log.info("【" + messageId + "】消息出现重复消费");
//            return;
//        }
//
//        // 重试次数
//        int retryCount = 0;
//        boolean success = false;
//
//        // 消费失败并且重试次数<=重试上限次数
//        while (!success && retryCount < MAX_RETRIES) {
//            retryCount++;
//            // 执行业务操作
//            success = true;
//            Thread.sleep(5000);
//            // 如果失败则重试
//            if (!success) {
//                String errorTip = "第" + retryCount + "次消费失败" +
//                        ((retryCount < 3) ? "," + RETRY_INTERVAL + "s后重试" : ",进入死信队列");
//                log.error(errorTip);
//                Thread.sleep(RETRY_INTERVAL * 1000);
//            }
//        }
//
//        if (success) {
//            //messageId存入redis
//            redisTemplate.opsForValue().set("rabbit-mq-"+messageId, messageId,24, TimeUnit.HOURS);
//            //确认消息消费成功
//            channel.basicAck(deliveryTag, false);
//            log.info("消费成功");
//        } else {
//            // 重试多次之后仍失败，进入死信队列
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//            log.info("重试多次之后仍失败，进入死信队列");
//        }
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        log.info("重试多次之后仍失败，进入死信队列");
    }

    /**
     * 消费者监听，绑定队列
     */
    @RabbitListener(queues = "topicSecondQueue")
    public void receiveTopicSecondMsg(JSONObject json) {
        //接收消息message
        log.info("Topic模式(topicSecondQueue)消费者收到消息: " + json.toJSONString());
    }

    /**
     * 消费者监听，绑定队列
     */
    @RabbitListener(queues = "fanoutFirstQueue")
    public void receiveFanoutFirstMsg(Map message) {
        //接收消息message
        log.info("Fanout模式(fanoutFirstQueue)消费者收到消息: " + message.toString());
    }

    /**
     * 消费者监听，绑定队列
     */
    @RabbitListener(queues = "fanoutSecondQueue")
    public void receiveFanoutSecondMsg(Map message) {
        //接收消息message
        log.info("Fanout模式(fanoutSecondQueue)消费者收到消息: " + message.toString());
    }

}