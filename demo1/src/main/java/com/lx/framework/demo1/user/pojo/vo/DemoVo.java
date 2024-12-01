package com.lx.framework.demo1.user.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-03-23  10:59
 * @Version 1.0
 */

//这个是类注解,表示该类实例化的对象里，值为null的字段不参与序列化，解决不同vo需要创建多个的问题
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DemoVo{

//    private static final long serialVersionUID = 1L;

    private String str1;

    private String str2;

    private String str3;


}