package com.lx.framework.demo1.dict.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.framework.demo1.dict.entity.DictValue;

import java.util.Map;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author lx
 * @since 2024-09-19
 */
public interface IDictValueService extends IService<DictValue> {


    Map<String, DictValue> getDictMapByType(Integer type);
    Map<String, DictValue> getDictMapByTypeV1(Integer type);
    Map<String, DictValue> getDictMapByTypeV2(Integer type);
    Map<String, DictValue> getDictMapByTypeV3(Integer type);

}
