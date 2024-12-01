package com.lx.framework.demo1.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lx.framework.demo1.user.entity.UserInfo;
import com.lx.framework.tool.orm.annotation.SensitiveField;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 账户表 Mapper 接口
 * </p>
 *
 * @author xin.liu
 * @since 2024-03-10
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    List<UserInfo> selectAll();

    void insert1(@SensitiveField @Param("idCard") String idCard, Long userInfoId);
    void insert2(List<UserInfo> userInfos);
//    void insert3(@Param("userInfos") List<UserInfo> userInfos);

}
