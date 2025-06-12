package com.lx.framework.demo1.redis.servcie;

import com.lx.framework.demo1.redis.hash.ConsistentHashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分片Redis服务，根据一致性哈希算法选择对应的Redis节点
 */
@Service
public class ShardedRedisService {

    @Autowired
    private ConsistentHashing consistentHashing;

    /**
     * 获取分片后的Key
     */
    public String getShardedKey(String originalKey) {
        String shardNode = consistentHashing.getShardNode(originalKey);
        // 生成格式为 "原始Key:分片ID" 的分片Key
        String shardId = shardNode.replace(":", "_");
        return originalKey + ":" + shardId;
    }

}