package com.lx.framework.demo1.utils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;


/**
 * @description 代码生成类
 * @author xin.liu
 * @date 2022/12/1 11:08
 */
public class GeneratorUtil {

    @Test
    public void codeGenerator() throws IOException {
        // 数据源配置
        DataSourceConfig.Builder DATA_SOURCE_CONFIG
                = new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF8&useSSL=false&allowMultiQueries=true&serverTimezone=Asia/Shanghai",
                "root",
                "123456");

        // 填充字段信息
//        List<IFill> tableFills = new ArrayList<>();
//        IFill uuid = new Column("UUID", FieldFill.INSERT);
//        IFill tenantUuid = new Column("TENANT_UUID", FieldFill.INSERT);
//        IFill customerUuid = new Column("CUSTOMER_UUID", FieldFill.INSERT);
//        IFill userUuid = new Column("USER_UUID", FieldFill.INSERT);
//        IFill createdBy = new Column("CREATED_BY", FieldFill.INSERT);
//        IFill createdTime = new Column("CREATED_TIME", FieldFill.INSERT);
//        IFill updatedBy = new Column("UPDATED_BY", FieldFill.INSERT_UPDATE);
//        IFill updatedTime = new Column("UPDATED_TIME", FieldFill.INSERT_UPDATE);
//        tableFills.add(uuid);
//        tableFills.add(tenantUuid);
//        tableFills.add(customerUuid);
//        tableFills.add(userUuid);
//        tableFills.add(createdBy);
//        tableFills.add(createdTime);
//        tableFills.add(updatedBy);
//        tableFills.add(updatedTime);

        // 需要做代码生成的表名

        String[] tableList={"user"};

        // 项目绝对路径
        File directory = new File("F:\\git project\\framework-demo\\demo1");
        String projectPath = directory.getCanonicalPath();

        // 项目的包名
        String packageName= "com.lx.framework.demo1.user1";

        // 代码生成

        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig(builder -> {
                    builder
                            .outputDir(projectPath+"\\src\\main\\java") // 输出目录
                            .author("xin.liu") // 作者名称
//                            .enableSpringdoc()
//                            .enableSwagger()
                            .disableOpenDir() //生成后不打开生成位置
                            .dateType(DateType.TIME_PACK)  // 时间策略
                            .commentDate("yyyy-MM-dd"); // 注释日期
                })
                // 包配置
                .packageConfig(builder -> {
                    builder
                            .parent(packageName) // 父包名
                            .entity("entity") // 实体类包名
                            .service("service") // service包名
                            .serviceImpl("service.impl") // serviceImpl包名
                            .mapper("mapper") // mapper包名
//                            .xml("mapper") //xml
                            .controller("controller") // controller包名
                            //自定义mapper.xml文件输出目录
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/mapper"));
                })
                // 策略配置
                .strategyConfig(builder -> {
                    builder
                            .enableCapitalMode() //驼峰
                            .enableSkipView() //跳过视图
                            .disableSqlFilter()
//                            .addTablePrefix("t_") // 增加过滤表前缀
//                            .addTableSuffix("_db") // 增加过滤表后缀
//                            .addFieldPrefix("t_") // 增加过滤字段前缀
//                            .addFieldSuffix("_field") // 增加过滤字段后缀
//                            .addInclude("test") // 表匹配
                            .addInclude(tableList)

                            // Controller 策略配置
                            .controllerBuilder()
//                            .enableFileOverride() //覆盖文件
                            .enableRestStyle() // 开启@RestController
                            .formatFileName("%sController") // Controller 文件名称
                            .enableHyphenStyle() //开启驼峰命名
                            // Service 策略配置
                            .serviceBuilder()
//                            .enableFileOverride()
                            .formatServiceFileName("%sService") // Service 文件名称
                            .formatServiceImplFileName("%sServiceImpl") // ServiceImpl 文件名称
                            // Mapper 策略配置
                            .mapperBuilder()
//                            .enableFileOverride()
                            .enableMapperAnnotation() // 开启@Mapper
                            .enableBaseColumnList() // 启用 columnList (通用查询结果列)
                            .enableBaseResultMap() // 启动resultMap
                            .formatMapperFileName("%sMapper") // Mapper 文件名称
                            .formatXmlFileName("%sMapper") // Xml 文件名称
                            // Entity 策略配置
                            .entityBuilder()
                            .enableLombok()
                            .enableFileOverride()
//                            .enableChainModel() // 链式  ???
                            .enableRemoveIsPrefix() // 开启boolean类型字段移除is前缀
                            .enableTableFieldAnnotation() //开启生成实体时生成的字段注解
//                            .versionColumnName("version") // 乐观锁数据库字段
//                            .versionPropertyName("version") // 乐观锁实体类名称
//                            .logicDeleteColumnName("delflag") // 逻辑删除数据库中字段名
//                            .logicDeletePropertyName("delFlag") // 逻辑删除实体类中的字段名
                            .naming(NamingStrategy.underline_to_camel) // 表名 下划线 -》 驼峰命名
                            .columnNaming(NamingStrategy.underline_to_camel) // 字段名 下划线 -》 驼峰命名
                            .idType(IdType.ASSIGN_ID) // 主键生成策略 雪花算法生成id
                            .formatFileName("%s") // Entity 文件名称
//                            .addTableFills(tableFills) // 表字段填充
                            .enableTableFieldAnnotation()
                    ;
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                // 配置模板
//                .templateConfig(builder -> {
//                    builder.controller("/templates/controller.java")
//                            .service("/templates/service.java")
//                            .serviceImpl("/templates/serviceImpl.java")
//                            .build();
//                })
                // 执行
                .execute();
    }
}