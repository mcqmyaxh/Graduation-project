package com.example.patient.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.math.BigInteger;

@Configuration
public class JacksonConfig {

    /**
     * 全局统一 JSON 格式配置
     */
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//        return builder -> {
//            // 1. Long 类型序列化为字符串（防止 JS 精度丢失）
//            SimpleModule module = new SimpleModule();
//            module.addSerializer(Long.class, ToStringSerializer.instance);
//            module.addSerializer(Long.TYPE, ToStringSerializer.instance);
//            builder.modules(module);
//
//            // 2. LocalDateTime 格式化
//            JavaTimeModule timeModule = new JavaTimeModule();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
//            builder.modules(timeModule);
//
//            // 3. 设置时区（可选）
//            builder.timeZone(TimeZone.getTimeZone("GMT+8"));
//
//            // 4. 可选：设置字段命名策略（如驼峰转下划线）
//            // builder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
//        };
//    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder)
    {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        // 全局配置序列化返回 JSON 处理
        SimpleModule simpleModule = new SimpleModule();
        //JSON Long ==> String
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }
}
