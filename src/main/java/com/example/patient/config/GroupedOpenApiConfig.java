package com.example.patient.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class GroupedOpenApiConfig {


    @Bean
    public GroupedOpenApi managementApi() {
        return GroupedOpenApi.builder()
                .group("管理平台模块") // 这个名字会显示在左上角的下拉框中
                .packagesToScan("com.example.patient.Management") // 指定扫描的包路径
                .build();
    }


    @Bean
    public GroupedOpenApi customerApi() {
        return GroupedOpenApi.builder()
                .group("用户平台模块") // 这个名字会显示在左上角的下拉框中
                .packagesToScan("com.example.patient.Customer") // 指定扫描的包路径
                .build();
    }

    @Bean
    public GroupedOpenApi registryApi() {
        return GroupedOpenApi.builder()
                .group("注册中心模块") // 这个名字会显示在左上角的下拉框中
                .packagesToScan("com.example.patient.registry.presentation.controller")
                .build();
    }
    @Bean
    public GroupedOpenApi h5Api() {
        return GroupedOpenApi.builder()
                .group("H5平台模块") // 这个名字会显示在左上角的下拉框中
                .packagesToScan("com.example.patient.H5") // 指定扫描的包路径
                .build();
    }

    @Bean
    public GroupedOpenApi testApi() {
        return GroupedOpenApi.builder()
                .group("后端测试用，前端不用理") // 这个名字会显示在左上角的下拉框中
                .packagesToScan("com.example.patient.test")
                .build();
    }

}
