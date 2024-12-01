package com.lx.framework.demo1.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.lx.framework.tool.orm.annotation.SensitiveData;
import com.lx.framework.tool.orm.annotation.SensitiveField;
import com.lx.framework.tool.orm.enums.SensitiveTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 账户表
 * </p>
 *
 * @author xin.liu
 * @since 2024-03-10
 */
@Getter
@Setter
@TableName("user_info")
@SensitiveData
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "user_info_id", type = IdType.AUTO)
    private Long userInfoId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 用户性别0男1女
     */
    @TableField("user_sex")
    private Integer userSex;

    /**
     * 证件类型1-身份证 2-军人证 3-护照 4-其他 
     */
    @TableField("id_type")
    private Integer idType;

    /**
     * 证件号
     */
    @TableField("id_card")
//    @SensitiveField(type = SensitiveTypeEnum.IDENTIFY)
    private String idCard;

    /**
     * 用户头像
     */
    @TableField("head_img")
    private String headImg;

    /**
     * 1正常 2 冻结 0删除
     */
    @TableField("user_status")
    private Integer userStatus;

    /**
     * 平台编码
     */
    @TableField("platform_code")
    private String platformCode;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Long createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Long updateTime;
}
