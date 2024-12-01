package com.lx.framework.demo1.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.framework.demo1.project.entity.Project;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 项目位置表 Mapper 接口
 * </p>
 *
 * @author xin.liu
 * @since 2024-11-20
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

}
