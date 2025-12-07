package com.example.patient;

import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.ColumnConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.core.handler.JacksonTypeHandler;
import com.zaxxer.hikari.HikariDataSource;

public class Codegen {
    public static void main(String[] args) {
        // 配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/patient?useInformationSchema=true&characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        // 创建全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBasePackage("com.example.patient.Management"); // 设置根包名
        globalConfig.setGenerateTable("base_hospital_dept_mapping"); // 指定生成哪些表//
        //KKHourse.sys_menu
        //KKHourse.sys_role
        //KKHourse.sys_role_menu
        //KKHourse.sys_user
        //KKHourse.sys_user_role
        //KKHourse.token
//        //根据表名，生成代码
//        globalConfig.setGenerateTable("sys_menu");
//        globalConfig.setGenerateTable("sys_role");
//        globalConfig.setGenerateTable("sys_role_menu");
//        globalConfig.setGenerateTable("sys_user");
//        globalConfig.setGenerateTable("sys_user_role");
//        globalConfig.setGenerateTable("token");


        // 启用 Entity 并配置 Lombok、JDK 版本
        globalConfig.enableEntity()
                .setWithLombok(true)
                .setJdkVersion(21);

        // 启用 Mapper 生成
        globalConfig.enableMapper();

        // 启用 Mapper XML 生成
        globalConfig.enableMapperXml();

        globalConfig.enableService();
        globalConfig.enableServiceImpl();

        // 配置 image_urls 字段
        ColumnConfig imageUrlsConfig = new ColumnConfig();
        imageUrlsConfig.setColumnName("image_urls");
        imageUrlsConfig.setTypeHandler(JacksonTypeHandler.class);
        imageUrlsConfig.setPropertyType("java.util.List<java.lang.String>");

// 配置 is_delete 字段
        ColumnConfig isDeleteConfig = new ColumnConfig();
        isDeleteConfig.setColumnName("is_delete");
        isDeleteConfig.setLogicDelete(true);

// 将配置分别绑定到表 generated_content 上
//        globalConfig.setColumnConfig("customer_official_account_post", imageUrlsConfig);
//        globalConfig.setColumnConfig("customer_official_account_post", isDeleteConfig);
//        globalConfig.setColumnConfig("customer_wechat_message", isDeleteConfig);

        // 创建生成器并执行
        Generator generator = new Generator(dataSource, globalConfig);
        generator.generate();
    }
}
