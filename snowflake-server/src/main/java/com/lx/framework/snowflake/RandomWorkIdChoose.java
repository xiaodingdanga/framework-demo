package com.lx.framework.snowflake;

import com.lx.framework.snowflake.entity.WorkCenterInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * 使用随机数获取雪花 WorkId
 */
@Slf4j
public class RandomWorkIdChoose extends AbstractWorkIdChooseTemplate implements InitializingBean {

    @Override
    protected WorkCenterInfo chooseWorkId() {
        int start = 0, end = 31;
        return new WorkCenterInfo(getRandom(start, end), getRandom(start, end));
    }

    @Override
    public void afterPropertiesSet() {
        chooseAndInit();
    }

    private static long getRandom(int start, int end) {
        long random = (long) (Math.random() * (end - start + 1) + start);
        return random;
    }
}