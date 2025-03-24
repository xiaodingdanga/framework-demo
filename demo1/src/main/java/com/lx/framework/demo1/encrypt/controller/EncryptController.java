package com.lx.framework.demo1.encrypt.controller;

import com.lx.framework.demo1.encrypt.servcie.impl.AESEncryptService;
import com.lx.framework.demo1.project.entity.ProjectLocation;
import com.lx.framework.demo1.project.mapper.ProjectLocationMapper;
import com.lx.framework.demo1.project.service.ProjectLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
 * @author xin.liu
 * @description TODO
 * @date 2024-03-23  15:37
 * @Version 1.0
 */
@RestController
@RequestMapping("/encrypt")
public class EncryptController {

//    @Autowired
//    private EncryptService encryptService;

    @Autowired
    private ProjectLocationMapper projectLocationMapper;


    @Autowired
    private ProjectLocationService projectLocationService;

    @Autowired
    private AESEncryptService aesEncryptService;


    @RequestMapping("/encrypt")
    public void encrypt() {
        System.out.println(aesEncryptService.encrypt("123456"));
//        System.out.println(sm4EncryptService.encrypt("123456"));
    }

    @RequestMapping("/en")
//    @Transactional(propagation = Propagation.NOT_SUPPORTED) // 禁用事务提升性能
    public void encryptAllUsers() {
        long l = System.currentTimeMillis();
        final int batchSize = 1000; // 根据测试调整
        final Long minId = projectLocationMapper.selectMinId();
        final Long maxId = projectLocationMapper.selectMaxId();
//        final Long maxId = 10000L;

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors() + 1);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("EncryptWorker-");
        executor.initialize();

        // 创建并行任务
//        List<CompletableFuture<Void>> futures = new ArrayList<>();
        List<Range> ranges = calculateRanges(minId, maxId, batchSize);
        CountDownLatch countDownLatch = new CountDownLatch(ranges.size());
        for (Range range : ranges) {
            System.out.println("开始处理：" + range.getStart() + " - " + range.getEnd());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
//            futures.add(CompletableFuture.runAsync(() -> processRange(range,batchSize), executor));
            executor.execute(() -> processRange(range, batchSize, countDownLatch));
        }
//        // 等待所有任务完成
//        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
//                .exceptionally(ex -> {
//                    throw new RuntimeException("加密任务失败", ex);
//                })
//                .join();
        try {
            countDownLatch.await();
            System.out.println("加密完成，耗时：" + (System.currentTimeMillis() - l) + "ms");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void processRange(Range range, int batchSize, CountDownLatch lacth) {
        try {
            Long currentStart = range.getStart();
            while (currentStart <= range.getEnd()) {
                // 分页查询（按主ID排序）
                List<ProjectLocation> projectLocations = projectLocationMapper.selectBatchByIdRange(currentStart, range.getEnd(), batchSize);

                // 加密并组装更新参数
                List<ProjectLocation> toUpdate = projectLocations.stream()
                        .map(projectLocation -> {
                            projectLocation.setProvinceId(aesEncryptService.encrypt(projectLocation.getProjectId().toString()));
                            return projectLocation;
                        })
                        .collect(Collectors.toList());
                // 批量更新（MyBatis-Plus的updateBatchById）
                projectLocationService.updateBatchById(toUpdate);
                // 移动到下一页（处理可能的空洞）
                currentStart = projectLocations.isEmpty() ? range.getEnd() + 1 : projectLocations.get(projectLocations.size() - 1).getId() + 1;
            }
            lacth.countDown();
        }catch (Exception e){
            e.printStackTrace();
            //todo 异常记录入库
        }

    }

    // 分页参数计算
    public List<Range> calculateRanges(Long minId, Long maxId, int batchSize) {
        List<Range> ranges = new ArrayList<>();
        for (Long i = minId; i <= maxId; i += batchSize) {
            ranges.add(new Range(i, Math.min(i + batchSize - 1, maxId)));
        }
        return ranges;
    }

}