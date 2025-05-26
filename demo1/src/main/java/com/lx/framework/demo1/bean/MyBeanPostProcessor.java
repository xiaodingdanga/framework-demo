package com.lx.framework.demo1.bean;

import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author xin.liu
 * @description TODO
 * @date 2025-05-08  11:00
 * @Version 1.0
 */
public class MyBeanPostProcessor implements BeanPostProcessor  {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("bean执行前: " + beanName);
        // 可以在这里对 Bean 进行操作
        return bean; // 返回原始 Bean 或修改后的 Bean
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("bean执行后: " + beanName);
        // 可以在这里对 Bean 进行操作
        return bean; // 返回原始 Bean 或修改后的 Bean
    }
}