package com.lx.framework.demo1.copy.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.lx.framework.demo1.copy.bean.Address;
import com.lx.framework.demo1.copy.bean.DeepAddress;
import com.lx.framework.demo1.copy.bean.DeepPerson;
import com.lx.framework.demo1.copy.bean.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

/**
 * @author xin.liu
 * @description TODO
 * @date 2025-05-26  15:30
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/copy")
public class CopyController {
    public static void main(String[] args) throws CloneNotSupportedException {
//        //浅拷贝
//        Address address = new Address("Main Street", 123);
//        Person person1 = new Person("Alice", 30, address);
//        // 使用clone进行浅拷贝
//        Person person2 = person1.clone();
//
//        // 使用BeanUtil和BeanUtils进行深拷贝
//        Person person3 = new Person();
//        BeanUtil.copyProperties(person1, person3);
//
//        // 使用BeanUtils进行深拷贝
//        Person person4 = new Person();
//        BeanUtils.copyProperties(person1, person4);
//
//        // 修改原始对象的字段
//        person1.setName("Bob");
//        person1.setAge(31);
//        address.setStreet("Oak Street");
//
//        System.out.println("person1: " + person1);
//        System.out.println("person2: " + person2);
//        System.out.println("person3: " + person3);
//        System.out.println("person4: " + person4);

        //通过序列化反序列化实现深拷贝
        DeepAddress deepAddress = new DeepAddress("Main Street", 123);
        DeepPerson deepPerson1 = new DeepPerson("Alice", 30, deepAddress);
//        DeepPerson deepPerson2 = SerializationUtils.clone(deepPerson1);

        // 通过JSON序列化实现深拷贝
        String jsonString = JSON.toJSONString(deepPerson1);
        DeepPerson deepPerson3 = JSON.parseObject(jsonString, DeepPerson.class);

        // 修改原始对象的字段
        deepPerson1.setName("Bob");
        deepPerson1.setAge(31);
        deepAddress.setStreet("Oak Street");

        System.out.println("deepPerson1: " + deepPerson1);
//        System.out.println("deepPerson2: " + deepPerson2);
        System.out.println("deepPerson3: " + deepPerson3);
    }

    // 深拷贝工具方法
    public static Object deepCopy(Object object) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            // 序列化
            oos.writeObject(object);

            try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                 ObjectInputStream ois = new ObjectInputStream(bais)) {

                // 反序列化
                return ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}