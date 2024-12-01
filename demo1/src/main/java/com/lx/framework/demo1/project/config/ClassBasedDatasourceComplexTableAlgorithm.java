package com.lx.framework.demo1.project.config;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-11-26  10:51
 * @Version 1.0
 */
public class ClassBasedDatasourceComplexTableAlgorithm implements ComplexKeysShardingAlgorithm<Comparable<?>> {
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Comparable<?>> shardingValue) {
        Map<String, Collection<Comparable<?>>> columnNameAndShardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();

        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());

        if(MapUtils.isNotEmpty(columnNameAndShardingValuesMap)){
            // 获取用户ID
            Collection<Comparable<?>> userIdCollection = columnNameAndShardingValuesMap.get("user_id");
            //用户分片
            if(CollectionUtils.isNotEmpty(userIdCollection)){
                userIdCollection.stream().findFirst().ifPresent(comparable -> {
                    System.out.println("自定义分片策略user_id:"+comparable);
                    long tableNameSuffix = (Long) comparable % 2;
                    result.add("master"+tableNameSuffix);
                });
            }else {
                Collection<Comparable<?>> orderSnCollection = columnNameAndShardingValuesMap.get("id");
                orderSnCollection.stream().findFirst().ifPresent(comparable -> {
//                    String orderSn = String.valueOf(comparable);
//                    //获取用户基因
//                    String substring = orderSn.substring(Math.max(0, orderSn.length() - 6));
//                    long tableNameSuffix = Long.parseLong(substring) % 2;
//                    result.add(shardingValue.getLogicTableName() + "_" + tableNameSuffix);
                    System.out.println("自定义分片策略id:"+comparable);
                    long tableNameSuffix = (Long) comparable % 2;
                    result.add("master"+tableNameSuffix);
                });
            }
        }
        return result;
    }
}