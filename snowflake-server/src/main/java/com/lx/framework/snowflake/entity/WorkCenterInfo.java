package com.lx.framework.snowflake.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WorkId 包装器
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkCenterInfo {
    /**
     * 工作ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long workId;

    /**
     * 数据中心ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dataCenterId;
}