package com.lx.framework.demo1.dict.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author lx
 * @since 2024-09-19
 */
@Getter
@Setter
@TableName("dict_value")
public class DictValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("parent_id")
    private Integer parentId;

    @TableField("type")
    private Integer type;

    @TableField("code")
    private String code;

    @TableField("name")
    private String name;

    @TableField("sort")
    private Integer sort;

    @TableField("description")
    private String description;

    @TableField("is_deleted")
    private Integer isDeleted;

    @TableField("create_by")
    private Long createBy;

    @TableField("create_time")
    private Long createTime;

    @TableField("update_by")
    private Long updateBy;

    @TableField("update_time")
    private Long updateTime;

    public static final String ID = "id";

    public static final String P_ID = "p_id";

    public static final String TYPE = "type";

    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String DESCRIPTION = "description";

    public static final String IS_DELETED = "is_deleted";

    public static final String CREATE_BY = "create_by";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_BY = "update_by";

    public static final String UPDATE_TIME = "update_time";

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", type=" + type +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", sort=" + sort +
                ", description='" + description + '\'' +
                ", isDeleted=" + isDeleted +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", updateBy=" + updateBy +
                ", updateTime=" + updateTime +
                '}';
    }
}
