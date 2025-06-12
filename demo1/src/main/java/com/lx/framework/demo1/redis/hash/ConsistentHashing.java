package com.lx.framework.demo1.redis.hash;

import cn.hutool.core.io.checksum.CRC16;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 一致性哈希算法实现，用于将热点Key分片到多个子节点
 */
@Component
public class ConsistentHashing {
    // 虚拟节点倍数，用于保证分布均匀性
    private static final int virtualNodeFactor = 100;
    // 存储虚拟节点的哈希环，使用TreeMap实现有序哈希
    private static final ConcurrentSkipListMap<Long, String> virtualNodes = new ConcurrentSkipListMap<>();
    // 真实节点列表
    private static final List<String> realNodes = Arrays.asList("localhost:6379","localhost:6380","localhost:6381");

//    public ConsistentHashing(int virtualNodeFactor, Collection<String> realNodes) {
//        this.virtualNodeFactor = virtualNodeFactor;
//        this.realNodes.addAll(realNodes);
//        // 初始化虚拟节点
//        initVirtualNodes();
//    }

    /**
     * 初始化虚拟节点
     */
    static {
        for (String node : realNodes) {
            for (int i = 0; i < virtualNodeFactor; i++) {
                // 为每个真实节点生成多个虚拟节点
                String virtualNodeName = getVirtualNodeName(node, i);
                long hash = hash(virtualNodeName);
                virtualNodes.put(hash, virtualNodeName);
            }
        }
    }

    /**
     * 根据原始Key获取对应的分片节点
     */
    public String getShardNode(String key) {
        long keyHash = hash(key);
        // 获取大于等于当前哈希值的第一个节点
        Map.Entry<Long, String> entry = virtualNodes.ceilingEntry(keyHash);
        if (entry == null) {
            // 如果没有比当前哈希值大的节点，则返回第一个节点
            entry = virtualNodes.firstEntry();
        }
        // 从虚拟节点名称中提取真实节点
        return getRealNodeName(entry.getValue());
    }

    /**
     * 添加新的节点
     */
    public void addNode(String node) {
        if (!realNodes.contains(node)) {
            realNodes.add(node);
            // 添加该节点对应的虚拟节点
            for (int i = 0; i < virtualNodeFactor; i++) {
                String virtualNodeName = getVirtualNodeName(node, i);
                long hash = hash(virtualNodeName);
                virtualNodes.put(hash, virtualNodeName);
            }
        }
    }

    /**
     * 移除节点
     */
    public void removeNode(String node) {
        if (realNodes.contains(node)) {
            realNodes.remove(node);
            // 移除该节点对应的虚拟节点
            for (int i = 0; i < virtualNodeFactor; i++) {
                String virtualNodeName = getVirtualNodeName(node, i);
                long hash = hash(virtualNodeName);
                virtualNodes.remove(hash);
            }
        }
    }

    /**
     * 生成虚拟节点名称
     */
    private static String getVirtualNodeName(String realNode, int index) {
        return realNode + "##VN" + index;
    }

    /**
     * 从虚拟节点名称提取真实节点
     */
    private String getRealNodeName(String virtualNodeName) {
        return virtualNodeName.split("##")[0];
    }

    /**
     * 使用FNV1_64算法计算哈希值
     */
    private static int hash(String key) {
//        final long FNV_64_INIT = 0xcbf29ce484222325L;
//        final long FNV_64_PRIME = 0x100000001b3L;
//
//        byte[] bytes = key.getBytes();
//        long hash = FNV_64_INIT;
//        for (byte b : bytes) {
//            hash ^= (b & 0xff);
//            hash *= FNV_64_PRIME;
//        }
//        return hash & 0xffffffffffffffffL; // 确保为正整数
        CRC16 crc16 = new CRC16();
        crc16.update(key.getBytes());
        return (int) (crc16.getValue() % virtualNodeFactor);
    }
}
