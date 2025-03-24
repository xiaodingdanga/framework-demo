package com.lx.framework.demo1.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.framework.demo1.project.entity.Project;
import com.lx.framework.demo1.project.entity.ProjectLocation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 项目位置表 Mapper 接口
 * </p>
 *
 * @author xin.liu
 * @since 2024-11-20
 */
@Mapper
public interface ProjectLocationMapper extends BaseMapper<ProjectLocation> {

    @Select("select * from project_location where id > #{startIdx} and id<= #{endIdx}")
    List<ProjectLocation> getPageAllList(@Param("startIdx")long startIdx, @Param("endIdx")long endIdx);

    @Select("SELECT MIN(id) FROM project_location")
    Long selectMinId();

    @Select("SELECT MAX(id) FROM project_location")
    Long selectMaxId();
    @Select("SELECT * FROM project_location WHERE id >= #{start} AND id <= #{end} ORDER BY id LIMIT #{batchSize}")
    List<ProjectLocation> selectBatchByIdRange(@Param("start") Long start, @Param("end") Long end, @Param("batchSize") int batchSize);

}
