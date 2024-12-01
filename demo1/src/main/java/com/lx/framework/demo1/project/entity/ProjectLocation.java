package com.lx.framework.demo1.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 项目位置表
 * </p>
 *
 * @author xin.liu
 * @since 2024-11-20
 */
@Getter
@Setter
@TableName("project_location")
public class ProjectLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目id
     */
    @TableField("project_id")
    private Long projectId;

    /**
     * 省份id
     */
    @TableField("province_id")
    private String provinceId;

    /**
     * 城市id
     */
    @TableField("city_id")
    private String cityId;
}
