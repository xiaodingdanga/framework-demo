package com.lx.framework.demo1.redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.framework.demo1.redis.entity.Test;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xin.liu
 * @since 2024-03-24
 */
@Mapper
public interface TestMapper extends BaseMapper<Test> {

}
