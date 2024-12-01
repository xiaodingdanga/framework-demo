package com.lx.framework.demo1.redis.servcie;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lx.framework.demo1.redis.entity.Test;

import java.util.List;

/**
* @author xin.liu
* @since 2024-03-24
*/
public interface TestService extends IService<Test> {

    /**
    * Test
    * @param
    * @return
    */
    Test getTest(Integer id);

    /**
    * Test
    * @param
    * @return
    */
    List<Test> getAllTest();

    /**
    * Test
    * @param test
    * @return
    */
    void add(Test test);

    /**
    * Test
    * @param test
    * @return
    */
    int modify(Test test);

    /**
    * Test
    * @param ids
    * @return
    */
    void remove(String ids);
}


