package com.lx.framework.demo1.redis.controller;

import cn.hutool.core.io.checksum.CRC16;
import com.alibaba.fastjson2.JSONObject;
import com.lx.framework.demo1.dict.entity.DictValue;
import com.lx.framework.demo1.dict.service.IDictValueService;
import com.lx.framework.demo1.redis.entity.Test;
import com.lx.framework.demo1.redis.mapper.TestMapper;
import com.lx.framework.demo1.redis.servcie.ShardedRedisService;
import com.lx.framework.demo1.utils.SnowflakeUtils;
import com.lx.framework.tool.startup.utils.Lock;
import com.lx.framework.tool.startup.utils.RedisLockUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xin.liu
 * @description redis基于lettuce实现redis分布式锁
 * @date 2024-02-28  14:03
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/redis")
public class RedisController {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ReactiveRedisTemplate reactiveRedisTemplate;

    @Autowired
    private RedisLockUtil redisLockUtil;

    @Autowired
    private TestMapper testMapper;

    @Resource
    private SnowflakeUtils snowflakeUtils;

    @Resource
    private ShardedRedisService shardedRedisService;

    @GetMapping("/set")
    public String set(String key) {

//        RedisUtil.lock()
//        redisTemplate.opsForValue().set("key","value");


        return redisTemplate.opsForValue().setIfAbsent(key, "123", 60000l, TimeUnit.SECONDS).toString();
    }

    @GetMapping("/get")
    public String get(String key) {

//        RedisUtil.lock()
//        redisTemplate.opsForValue().set("key","value");
        String s = stringRedisTemplate.opsForValue().get(key);
        return s;
    }

    @GetMapping("/lock")
    public String lock() throws InterruptedException {
        Lock lock = redisLockUtil.lock("key", "value", 5);
        Test test1 = testMapper.selectById(137);
        int i = test1.getCount() + 1;
        test1.setCount(i);
        Thread.sleep(500);
        testMapper.updateById(test1);
        redisLockUtil.unlock(lock);
        System.out.println(Thread.currentThread().getName() + "执行成功1！");
        return "ok";
    }

    @Cacheable(cacheNames = "dict-value", key = "#p0 + '_' + #p0")
    @PostMapping("/testCaceh")
    public JSONObject testCaceh(@RequestParam(value = "type") Integer type, @RequestParam(value = "parentId") Integer parentId) {
        String str = "{\"responseCode\":100000000,\"responseMessage\":\"查询成功\",\"responseData\":[{\"id\":42061,\"name\":\"陕西省\",\"children\":null},{\"id\":28625,\"name\":\"广东省\",\"children\":null},{\"id\":30494,\"name\":\"广西壮族自治区\",\"children\":null},{\"id\":31925,\"name\":\"海南省\",\"children\":null},{\"id\":32175,\"name\":\"重庆市\",\"children\":null},{\"id\":33248,\"name\":\"四川省\",\"children\":null},{\"id\":38101,\"name\":\"贵州省\",\"children\":null},{\"id\":39685,\"name\":\"云南省\",\"children\":null},{\"id\":41272,\"name\":\"西藏自治区\",\"children\":null},{\"id\":26456,\"name\":\"湖南省\",\"children\":null},{\"id\":43500,\"name\":\"甘肃省\",\"children\":null},{\"id\":45050,\"name\":\"青海省\",\"children\":null},{\"id\":45536,\"name\":\"宁夏回族自治区\",\"children\":null},{\"id\":45834,\"name\":\"新疆维吾尔自治区\",\"children\":null},{\"id\":94766,\"name\":\"河北保定\",\"children\":null},{\"id\":317148,\"name\":\"台湾省\",\"children\":null},{\"id\":317149,\"name\":\"香港特别行政区\",\"children\":null},{\"id\":317150,\"name\":\"澳门特别行政区\",\"children\":null},{\"id\":11953,\"name\":\"江苏省\",\"children\":null},{\"id\":812,\"name\":\"天津市\",\"children\":null},{\"id\":1135,\"name\":\"河北省\",\"children\":null},{\"id\":3697,\"name\":\"山西省\",\"children\":null},{\"id\":5327,\"name\":\"内蒙古自治区\",\"children\":null},{\"id\":6726,\"name\":\"辽宁省\",\"children\":null},{\"id\":8445,\"name\":\"吉林省\",\"children\":null},{\"id\":9570,\"name\":\"黑龙江省\",\"children\":null},{\"id\":11701,\"name\":\"上海市\",\"children\":null},{\"id\":458,\"name\":\"北京市\",\"children\":null},{\"id\":13627,\"name\":\"浙江省\",\"children\":null},{\"id\":15140,\"name\":\"安徽省\",\"children\":null},{\"id\":16938,\"name\":\"福建省\",\"children\":null},{\"id\":18227,\"name\":\"江西省\",\"children\":null},{\"id\":20133,\"name\":\"山东省\",\"children\":null},{\"id\":22176,\"name\":\"河南省\",\"children\":null},{\"id\":24954,\"name\":\"湖北省\",\"children\":null}],\"requestId\":\"4b3a51f2-3b35-4bd4-8fab-536ed16bc7b5\"}";
        JSONObject jsonObject1 = JSONObject.parseObject(str);
        return jsonObject1;
    }

    @GetMapping("/api")
    public String api() {
        //string
        ValueOperations valueOperations = redisTemplate.opsForValue();
//        valueOperations.set("int", 1);
//        valueOperations.set("string", "string");
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("string");
//        strings.add("int");
        //批量获取值
//        List list = valueOperations.multiGet(strings);
        //从指定位置获取值
//        String s = valueOperations.get("string", 0, 1);
        //批量设置值
//        HashMap<String, String> stringStringHashMap = new HashMap<>();
//        stringStringHashMap.put("ma", "ma");
//        stringStringHashMap.put("mb", "mb");
//        valueOperations.multiSet(stringStringHashMap);
        //在指定的偏移量位置设置字符串值的一部分
//        valueOperations.set("string", "string",2);
        //在末尾追加字符串
//        valueOperations.append("string","1");
        //如果键存在则返回false，否则返回true
//        Boolean b = valueOperations.setIfAbsent("string1", "string");
        //如果键存在并且成功设置了新值，则返回 true；如果键不存在，则返回 false
//        Boolean b = valueOperations.setIfPresent("string2", "string23423");
        //位操作
//        Boolean b = valueOperations.setBit("bit", 1, true);
//        System.out.println("-----------------:"+b);
//        Boolean bit = valueOperations.getBit("bit", 0);
//        System.out.println("-----------------:"+bit);
        //减一
//        valueOperations.decrement("int");
        //加一
//        valueOperations.increment("int");
//
//        Long string = valueOperations.size("string");
//        System.out.println(string);

        //list
        ListOperations listOperations = redisTemplate.opsForList();

//        listOperations.leftPush("list", "string7");
//        listOperations.set("list", 1, "string1");
//        Object list = listOperations.index("list", 1);
//        System.out.println(list.toString());
//        Object list = listOperations.leftPop("list");
//        System.out.println(list.toString());
//        Object list1 = listOperations.rightPop("list");
//        System.out.println(list1.toString());
//        Long l = listOperations.indexOf("list", "string");
//        System.out.println(l);
//        List list = listOperations.range("list", 0, 3);
//        System.out.println(list);
//        listOperations.rightPopAndLeftPush("list", "list1");
//        listOperations.remove("list", 1, "string7");
//        listOperations.move( "list", RedisListCo mmands.Direction.first(), "list1", RedisListCommands.Direction.last());

        //set
        SetOperations setOperations = redisTemplate.opsForSet();
//        setOperations.add("set", "string1", "string2", "string3", "string4", "string5", "string6", "string7");
//        setOperations.add("set1", "string2");
//        Set difference = setOperations.difference("set", "set1");
//        System.out.println(difference);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("set");
        strings.add("set1");
        Set difference1 = setOperations.difference(strings);
        System.out.println(difference1);


//        //zset
//        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

//        //hash
//        HashOperations hashOperations = redisTemplate.opsForHash();

        return "success";
    }

    @Autowired
    private IDictValueService iDictValueService;

    /*
     * @description TODO
     * @param type 1-6
     * @param method 0-不走redis 1-加密 2-分批 3-原始数据存入
     * @return: java.lang.String
     * @author xin.liu
     * @date 2025/5/13 10:35
     */
    @GetMapping("/testCache")
    public String test1(@RequestParam("type") Integer type, @RequestParam("method") Integer method) {
        //计算并打印接口耗时
        long startTime = System.currentTimeMillis();
        Map<String, DictValue> dictMapByType = new HashMap<>();
        if (method == 0) {
            dictMapByType = iDictValueService.getDictMapByType(type);
        } else if (method == 1) {
            dictMapByType = iDictValueService.getDictMapByTypeV1(type);
        } else if (method == 2) {
            dictMapByType = iDictValueService.getDictMapByTypeV2(type);
        } else if (method == 3) {
            dictMapByType = iDictValueService.getDictMapByTypeV3(type);
        }
//        for (Map.Entry<String, DictValue> entry : dictMapByType.entrySet()) {
//            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
//        }
        System.out.println("字典map数量：" + dictMapByType.size());
        System.out.println("接口耗时：" + (System.currentTimeMillis() - startTime) + "ms");
        return "success";
    }

    @GetMapping("/bitmap")
    public void bitmap() {
        stringRedisTemplate.opsForValue().setBit("bitmap", 1000, true);
    }

    @GetMapping("/checkin1")
    public String checkIn1(@RequestParam("userId") Long userId, @RequestParam("day") int day) {
        checkIn(userId, day);
        return "签到成功";
    }

    @GetMapping("/checkin/count")
    public String countCheckIn(@RequestParam("userId") Long userId) {
        Long count = countCheckInDays(userId);
        return "本月已签到：" + count + " 天";
    }

    @GetMapping("/checkin/detail")
    public String checkInDetail(@RequestParam("userId") Long userId) {
        return getDailyCheckIns(userId);
    }

    /**
     * 用户在某月的某一天签到
     *
     * @param userId 用户ID
     * @param day    日期 (1-31)
     */
    public void checkIn(Long userId, int day) {
        String key = "user:checkin:" + userId + ":202504"; // 按月份作为key的一部分
        stringRedisTemplate.opsForValue().setBit(key, day - 1, true); // offset从0开始
    }

    /**
     * 查询用户在某月的某一天是否已签到
     *
     * @param userId 用户ID
     * @param day    日期 (1-31)
     * @return 是否签到
     */
    public boolean isCheckIn(Long userId, int day) {
        String key = "user:checkin:" + userId + ":202504";
        return stringRedisTemplate.opsForValue().getBit(key, day - 1);
    }

    /**
     * 统计用户在某月的总签到天数
     *
     * @param userId 用户ID
     * @return 签到天数
     */
    public Long countCheckInDays(Long userId) {
        String key = "user:checkin:" + userId + ":202504";
        return stringRedisTemplate.execute((RedisCallback<Long>) connection ->
                connection.bitCount(key.getBytes()));
    }

    /**
     * 获取用户在某月的每日签到情况
     *
     * @param userId 用户ID
     * @return 每日签到情况数组（索引0表示第1天）
     */
    public String getDailyCheckIns(Long userId) {
        String key = "user:checkin:" + userId + ":202504";
        byte[] data = stringRedisTemplate.execute((RedisCallback<byte[]>) connection ->
                connection.get(key.getBytes()));

        StringBuilder sb = new StringBuilder();
        sb.append("用户 ").append(userId).append("在 2025年4月 的签到情况：\n");

        if (data == null) {
            sb.append("该月无签到记录，所有日期均未签到。\n");
            return sb.toString();
        }

        for (int i = 0; i < 31 && i < data.length * 8; i++) {
            int byteIndex = i / 8;
            int bitIndex = 7 - (i % 8); // 反转 bit 顺序
            boolean isCheckIn = ((data[byteIndex] & (1 << bitIndex)) != 0);
            sb.append("第 ").append(i + 1).append(" 天：").append(isCheckIn ? "已签到" : "未签到").append("\n");
        }

        return sb.toString();
    }
//====================================================================================

    @GetMapping("/checkin/day")
    public String checkInByDay(@RequestParam("userId") Long userId,
                               @RequestParam("date") String date) {
        checkInByDayAsKey(userId, date);
        return "签到成功";
    }

    @GetMapping("/checkin/status/day")
    public String checkInStatusByDay(@RequestParam("userId") Long userId,
                                     @RequestParam("date") String date) {
        boolean isCheckIn = isCheckInByDay(userId, date);
        return isCheckIn ? "已签到" : "未签到";
    }

    @GetMapping("/checkin/count/day")
    public String countCheckInByDay(@RequestParam("date") String date) {
        Long count = countCheckInUsers(date);
        return "今日签到人数：" + count;
    }

    @GetMapping("/checkin/getUser")
    public Set<Long> getUser(@RequestParam("date") String date) {
        return getAllCheckInUsers(date, 100l);
    }

    /**
     * 查询用户在某一天是否签到
     *
     * @param userId 用户ID
     * @param date   日期，格式为 yyyyMMdd
     * @return 是否签到
     */
    public boolean isCheckInByDay(Long userId, String date) {
        String key = "sign:" + date;
        return stringRedisTemplate.opsForValue().getBit(key, userId);
    }

    /**
     * 用户在某一天签到
     *
     * @param userId 用户ID
     * @param date   日期，格式为 yyyyMMdd，如 20250401
     */
    public void checkInByDayAsKey(Long userId, String date) {
        String key = "sign:" + date; // 按天作为 key
        stringRedisTemplate.opsForValue().setBit(key, userId, true); // 用户ID作为offset
    }

    /**
     * 统计某天的签到人数
     *
     * @param date 日期，格式为 yyyyMMdd
     * @return 签到人数
     */
    public Long countCheckInUsers(String date) {
        String key = "sign:" + date;
        return stringRedisTemplate.execute((RedisCallback<Long>) connection ->
                connection.bitCount(key.getBytes()));
    }

    /**
     * 获取某天所有签到用户ID列表（根据 offset）
     *
     * @param date      日期
     * @param maxUserId 最大用户ID（用于限制遍历范围）
     * @return 签到用户ID集合
     */
    public Set<Long> getAllCheckInUsers(String date, long maxUserId) {
        String key = "sign:" + date;
        byte[] data = stringRedisTemplate.execute((RedisCallback<byte[]>) connection ->
                connection.get(key.getBytes()));

        Set<Long> signedUsers = new HashSet<>();
        if (data == null) return signedUsers;

        for (long i = 0; i <= maxUserId && i < data.length * 8; i++) {
            int byteIndex = (int) (i / 8);
            int bitIndex = 7 - (int) (i % 8); // 反转 bitIndex

            if ((data[byteIndex] & (1 << bitIndex)) != 0) {
                signedUsers.add(i);
            }
        }

        return signedUsers;
    }

    //============================reids集群槽位分配========================================
    private static final int TOTAL_SLOTS = 16384;

    // 计算CRC16并返回槽位ID
    public static int calculateSlot(String key) {
        CRC16 crc16 = new CRC16();
        crc16.update(key.getBytes());
        return (int) (crc16.getValue() % TOTAL_SLOTS);
    }

    // 生成映射到指定槽位的Hash Tag
    public static String generateHashTagForSlot(int targetSlot) {
        int attempt = 0;
        while (true) {
            String hashTag = "slot" + targetSlot + "_" + attempt;
            int slot = calculateSlot(hashTag);
            if (slot == targetSlot) {
                return hashTag;
            }
            attempt++;
        }
    }

    @GetMapping("/shard")
    public void shard() {
        // 模拟1000个热点Key的分布情况
        Map<String, String> distribution = new HashMap<>();
        int totalKeys = 1000;

        for (int i = 0; i < totalKeys; i++) {
            String originalKey = "hotkey:" + i;
            String shardedKey = shardedRedisService.getShardedKey(originalKey);
            System.out.println("原始Key: " + originalKey + ", 分片Key: " + shardedKey);
            // 提取分片ID
            String shardId = shardedKey.split(":")[1];
            distribution.put(shardId, shardedKey.split(":")[2]);
        }

        Map<String, Integer> valueCounts = new HashMap<>();

        // 统计每个 value 出现的次数
        System.out.println("热点Key分布情况:");
        for (Map.Entry<String, String> entry : distribution.entrySet()) {
            String value = entry.getValue();
            valueCounts.put(value, valueCounts.getOrDefault(value, 0) + 1);
            System.out.println("keyID: " + entry.getKey() + ", 分片名称: " + entry.getValue());
        }
        //打印分布情况
        for (Map.Entry<String, Integer> entry : valueCounts.entrySet()) {
            System.out.println("分片名称: " + entry.getKey() + ", key数量: " + entry.getValue());
        }
    }

    public static void main(String[] args) {
//        //计算执行时间
//        //循环十次
//        for (int i = 0; i < 10; i++) {
//            long startTime = System.currentTimeMillis();
//            System.out.println(generateHashTagForSlot(i+10000));
//            long endTime = System.currentTimeMillis();
//            System.out.println("执行时间：" + (endTime - startTime) + "毫秒");
//        }

//        String originalKey = "hot_key";
//        int splitNum = 18;
//        List<String> subKeys = new ArrayList<>();
//
//        // 生成子key（随机后缀）
//        for (int i = 0; i < splitNum; i++) {
//            String suffix = UUID.randomUUID().toString().substring(0, 4);
//            subKeys.add(originalKey + ":" + suffix);
//            subKeys.add(originalKey + ":" + i);
//        }
//
//        List<Integer> slots = new ArrayList<>();
//        // 计算每个子key的槽位并输出
//        for (String subKey : subKeys) {
//            int crc16 = CRC16.crc16(subKey.getBytes());
//            int slot = crc16 % 16384;
//            System.out.println("子key: " + subKey + "，槽位: " + slot);
//            slots.add(slot);
//        }
//
//        // 对slots排序输出
//        slots.sort(Integer::compareTo);
//        System.out.println("排序后的槽位: " + slots);
    }

}
