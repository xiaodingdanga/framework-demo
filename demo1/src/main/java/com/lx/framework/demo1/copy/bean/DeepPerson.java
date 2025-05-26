package com.lx.framework.demo1.copy.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xin.liu
 * @description TODO
 * @date 2025-05-26  15:31
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeepPerson implements Serializable {
    private String name;
    private int age;
    private DeepAddress deepAddress;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + deepAddress +
                '}';
    }
}