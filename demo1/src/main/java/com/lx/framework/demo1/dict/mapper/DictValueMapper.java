package com.lx.framework.demo1.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.framework.demo1.dict.entity.DictValue;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author lx
 * @since 2024-09-19
 */
@Mapper
public interface DictValueMapper extends BaseMapper<DictValue> {

}
