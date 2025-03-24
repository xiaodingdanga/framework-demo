package com.lx.framework.demo1.export.controller;

import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.framework.demo1.project.entity.ProjectLocation;
import com.lx.framework.demo1.project.mapper.ProjectLocationMapper;
import com.lx.framework.demo1.project.service.ProjectLocationService;
import com.lx.framework.demo1.project.service.ProjectService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author xin.liu
 * @since 2024-11-20
 */
@RestController

@RequestMapping("/export")
public class ExportController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectLocationService projectLocationService;

    @Autowired
    private ProjectLocationMapper projectLocationMapper;

    @GetMapping("/bigdata")
    public void export(HttpServletResponse response) {
        String fileName = "export_" + System.currentTimeMillis();
        long startTime = System.currentTimeMillis();
        //通过工具类创建writer
        //创建writer
//        ExcelWriter writer = ExcelUtil.getWriter();
//        SXSSFWorkbook workbook = new SXSSFWorkbook(500); // 自定义窗口大小
        //数据量特别大时，使用BigExcelWriter对象，可以避免内存溢出
        BigExcelWriter writer = ExcelUtil.getBigWriter();
        // Hutool默认设置rowAccessWindowSize=100
//        BigExcelWriter writer = new BigExcelWriter(workbook,"sheet1");
        try {
            //设置sheet的名称
            writer.renameSheet("sheet1");
            Map<String, String> headers = new LinkedHashMap<>();
            headers.put("id", "id");
            headers.put("projectId", "姓名");
            headers.put("provinceId", "年龄");
            headers.put("cityId", "年龄1");
            //设置head的名称， 此时的顺寻就是导出的顺序， key就是RecordInfoDetailsDTO的属性名称， value就是别名
            headers.entrySet().forEach(entry -> {
                //这个添加顺序和导出顺序相同
                writer.addHeaderAlias(entry.getKey(), entry.getValue());
            });
            //设置宽度 0代表第一列 1代表第二列 以此类推
            writer.setColumnWidth(1,30);
            writer.setColumnWidth(4,30);
            /**
             * 设置是否只保留别名中的字段值，如果为true，则不设置alias的字段将不被输出，false表示原样输出
             * 这一行很重要 如果不设置只要实体中的属性值 大于 “自定义标题别名” 在Excel中就会多出一列ID字段
             */
            writer.setOnlyAlias(true);
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
            Long l = projectLocationMapper.selectCount(new QueryWrapper<>());
            int count = Integer.parseInt(l.toString());
            int size = 20000;
            int cycles =count / size +1;

            //多线程查
            CountDownLatch latch = new CountDownLatch(Math.toIntExact(cycles));
//            ExecutorService executorService = Executors.newFixedThreadPool(Math.toIntExact(cycles));
            // 线程工厂
            ThreadFactory threadFactory = Executors.defaultThreadFactory();

            // 3. 配置线程池（带监控的包装类）
            ExecutorService executorService = new ThreadPoolExecutor(
                    Runtime.getRuntime().availableProcessors() * 2,
                    Runtime.getRuntime().availableProcessors() * 4,
                    60L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(1000),
                    threadFactory,
                    new ThreadPoolExecutor.CallerRunsPolicy()
            );

            for (int i = 0; i < cycles; i++) {
                final int currentIndex = i;
                System.out.println("线程数量i:"+i+1);
                executorService.submit(() -> {
                    int startIdx = currentIndex * size;
                    int endIdx = (currentIndex + 1) * size;
                    if (endIdx > count) {
                        endIdx = Math.toIntExact(count);
                    }
                    List<ProjectLocation> rows = projectLocationMapper.getPageAllList(startIdx, endIdx);
                    // 一次性写出内容，使用默认样式，强制输出标题
                    writer.write(rows, false);
                    System.out.println("i:" + endIdx);
                    latch.countDown(); // 通知完成
                });
            }


            //分页查
//            // 初始化分页参数
//            int i = 1;
//            while (i<500000){
//                rows = new LambdaQueryChainWrapper<>(projectLocationService.getBaseMapper())
//                        .ge(ProjectLocation::getId, i)
//                        .orderByAsc(ProjectLocation::getId)
//                        .last("LIMIT 20000")
//                        .list();
//                // 一次性写出内容，使用默认样式，强制输出标题
//                if(i == 1){
//                    writer.write(rows, true);
//                }else{
//                    writer.write(rows, false);
//                }
//                i = rows.getLast().getId();
//                System.out.println("i:"+i);
//            }

            //全量查
//            rows = new LambdaQueryChainWrapper<>(projectLocationService.getBaseMapper())
//                    .le(ProjectLocation::getId, 500000)
//                    .list();
//            // 一次性写出内容，使用默认样式，强制输出标题
//            writer.write(rows, true);


            try {
                // 等待所有任务完成
                latch.await();

                // 刷盘与清理
                writer.flush(response.getOutputStream());
                //中文名称需要特殊处理
                writer.close();
                long endTime = System.currentTimeMillis();
                System.out.println("hutool 写入记录耗时 " + (endTime - startTime) / 1000 + "秒");

                // 没有异常发生，开始关闭线程池
                executorService.shutdown();
                // 等待线程池关闭完成，或者达到超时时间
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    // 如果超时，强制关闭线程池
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                // 发生中断异常，重新设置中断状态
                Thread.currentThread().interrupt();
                // 尝试关闭线程池
                executorService.shutdownNow();
            }
        } catch (Exception e) {
            //如果导出异常，则生成一个空的文件
            e.printStackTrace();
            writer.close();
        }
    }
}
