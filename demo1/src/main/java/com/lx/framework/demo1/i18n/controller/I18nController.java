package com.lx.framework.demo1.i18n.controller;

import com.lx.framework.tool.startup.utils.LocaleUtil;
import com.lx.framework.tool.startup.utils.RequestUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-03-23  15:37
 * @Version 1.0
 */
@RestController
@RequestMapping("/i18n")
public class I18nController {


    @RequestMapping("/test")
    public String writeReturnJson(HttpServletResponse response){
        return LocaleUtil.getI18n("global.error", null, RequestUtil.getLanguage());
    }
}