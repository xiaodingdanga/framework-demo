package com.lx.framework.demo1.base.controller;

import com.alibaba.fastjson2.JSONObject;
import com.lx.framework.tool.startup.utils.ResponseUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-03-23  15:37
 * @Version 1.0
 */
@RestController
@RequestMapping("/util")
public class UtilController {


    @RequestMapping("/writeReturnJson")
    public void writeReturnJson(HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key","value");
        ResponseUtil.writeReturnJson(response,jsonObject.toJSONString());
    }
}