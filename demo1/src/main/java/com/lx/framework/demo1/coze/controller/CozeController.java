package com.lx.framework.demo1.coze.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xin.liu
 * @description redis基于lettuce实现redis分布式锁
 * @date 2024-02-28  14:03
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/coze")
public class CozeController {

    @RequestMapping(value = "/workflow/run")
    public String test() {
        // 定义目标URL
        String url = "https://api.coze.cn/v1/workflow/run";

        // 创建请求参数
        String str = "{\n" +
                "  \"parameters\": {\n" +
                "    \"BOT_USER_INPUT\": \" 如可学习使用扣子\",\n" +
                "    \"CONVERSATION_NAME\": \"Default\"\n" +
                "  },\n" +
                "  \"workflow_id\": \"7507102156417335335\",\n" +
                "  \"bot_id\": \"7507152864931233829\"\n" +
                "}";
        JSONObject params = JSONObject.parseObject(str);

        // 发送POST请求并获取响应结果
        String response = HttpRequest.post(url)
                .header("Authorization", "Bearer pat_2Q7ojfyMI3AurbTbHiOGYVc56dZjl8mLowCvBOWSsMpUxAn63Uz4QvC0RR9ARWQj")
                .header("Content-Type", "application/json")
                .body(params.toJSONString())
                .execute()
                .body();

        // 输出响应结果
        System.out.println(response);
        return response;
    }


}