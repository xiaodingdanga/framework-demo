package com.lx.framework.demo1.copy.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xin.liu
 * @description TODO
 * @date 2025-05-26  15:31
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Cloneable {
    private String name;
    private int age;
    private Address address;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                '}';
    }

    @Override
    public Person clone() throws CloneNotSupportedException {
        // 调用super.clone()创建一个浅拷贝的对象
        return (Person) super.clone();
    }
}