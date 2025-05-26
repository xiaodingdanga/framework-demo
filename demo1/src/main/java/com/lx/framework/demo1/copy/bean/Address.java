package com.lx.framework.demo1.copy.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xin.liu
 * @description TODO
 * @date 2025-05-26  15:35
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String street;
    private int houseNumber;

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                '}';
    }
}