package com.lx.framework.demo1.redis.servcie.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.framework.demo1.redis.entity.Test;
import com.lx.framework.demo1.redis.mapper.TestMapper;
import com.lx.framework.demo1.redis.servcie.TestService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Arrays;
/**
* 
*
* @author xin.liu
* @since 2024-03-24
*/
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

    @Autowired
    TestMapper testMapper;

    @Override
    public Test getTest(Integer id){
        return testMapper.selectById(id);
    }

    @Override
    public List<Test> getAllTest(){
        return testMapper.selectList(null);
    }

    @Override
    public void add(Test test) {
        testMapper.insert(test);
    }

    @Override
    public int modify(Test test) {
        return  testMapper.updateById(test);
    }

    @Override
    public void remove(String ids) {
        if(StringUtils.isNotEmpty(ids)){
            String[] array = ids.split(",");
            if (!CollectionUtils.isEmpty(Arrays.asList(array))) {
                testMapper.deleteBatchIds(Arrays.asList(array));
            }
        }
    }
}
