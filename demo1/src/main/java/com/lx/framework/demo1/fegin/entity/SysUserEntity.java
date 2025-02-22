package com.lx.framework.demo1.fegin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lx
 * @since 2023-08-14
 */
@Getter
@Setter
@TableName("sys_user")
public class SysUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @TableField("account")
    private String account;

    @TableField("password")
    private String password;

    @TableField("name")
    private String name;

    @TableField("tel")
    private String tel;

    @TableField("email")
    private String email;

    @TableField("isdel")
    private String isdel;

    @TableField("des")
    private String des;

    @TableField("sort")
    private Integer sort;

    @TableField("customerid")
    private String customerid;

    @TableField("createrid")
    private String createrid;

    @TableField("isshow")
    private String isshow;

    @TableField("shopId")
    private String shopId;

    /**
     * 分销价格
     */
    @TableField("traderprice")
    private String traderprice;

    /**
     * 所属分销商
     */
    @TableField("createcustomer")
    private String createcustomer;

    /**
     * 明文密码
     */
    @TableField("realpassword")
    private String realpassword;


}
