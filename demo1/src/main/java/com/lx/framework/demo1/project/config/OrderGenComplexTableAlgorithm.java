package com.lx.framework.demo1.project.config;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-11-26  10:51
 * @Version 1.0
 */
public class OrderGenComplexTableAlgorithm implements ComplexKeysShardingAlgorithm<Comparable<?>> {
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Comparable<?>> shardingValue) {
        Map<String, Collection<Comparable<?>>> columnNameAndShardingValuesMap = shardingValue.getColumnNameAndShardingValuesMap();

        Collection<String> result = new LinkedHashSet<>(availableTargetNames.size());

        if(MapUtils.isNotEmpty(columnNameAndShardingValuesMap)){
            // 获取用户ID
            Collection<Comparable<?>> userIdCollection = columnNameAndShardingValuesMap.get("customer_id");
            //用户分片
            if(CollectionUtils.isNotEmpty(userIdCollection)){
                userIdCollection.stream().findFirst().ifPresent(comparable -> {
                    long tableNameSuffix = (Long) comparable % 2;
                    result.add(shardingValue.getLogicTableName() + "_" + tableNameSuffix);
                });
            }else {
                Collection<Comparable<?>> orderSnCollection = columnNameAndShardingValuesMap.get("order_sn");
                orderSnCollection.stream().findFirst().ifPresent(comparable -> {
                    String orderSn = String.valueOf(comparable);
                    //获取用户基因
                    String substring = orderSn.substring(Math.max(0, orderSn.length() - 6));
                    long tableNameSuffix = Long.parseLong(substring) % 2;
                    result.add(shardingValue.getLogicTableName() + "_" + tableNameSuffix);
                });
            }
        }
        return result;
    }

    @Override
    public void init(Properties properties) {

    }
}