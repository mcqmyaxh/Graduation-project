package com.example.patient.config;

import com.mybatisflex.core.mybatis.FlexConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class MyBatisFlexConfig {
    //    classpath*:mapper/*.xml, classpath*:com/example/kkhourse/**/repository/mapper/*.xml
    // =============== 1. SqlSessionFactory 配置 ===============
    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(
            DataSource dataSource) throws Exception {

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        // 设置 Mapper XML 文件路径
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(
                        "classpath*:mapper/*.xml"
                )
        );

        // 创建 FlexConfiguration 并应用自定义配置
        FlexConfiguration configuration = new FlexConfiguration();
        factoryBean.setConfiguration(configuration);

        return factoryBean.getObject();
    }

    // =============== 2. Mapper 扫描配置 ===============
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        // 扫描所有 com.example.kkhourse 下任意子模块中的 mapper 包
        configurer.setBasePackage("com.example.patient.*.mapper");
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");

        return configurer;
    }

    // =============== 3. 事务管理器配置 ===============
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
//    @Bean
//    public FlexConfigurationCustomizer myConfigurationCustomizer() {
//        return configuration -> {
//            // 关键：关闭 Flex 对 FlexDataSource 的强依赖
//            configuration.setEnableNativeObject(false); // 非常重要！
//
//            // 可选：开启日志
//            configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
//
//            // 其他配置...
//            // configuration.setMapUnderscoreToCamelCase(true);
//        };
//    }



    // 如果 StdOutImpl 报错，请确认是否正确引入或使用其他日志实现
    // 示例：configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
}
