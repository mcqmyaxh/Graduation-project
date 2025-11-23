package com.example.patient.config;/**
*@Auter zzh
*@Date 2025/4/1
*/

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @projectName:    tiedlink_java 
 * @package:        com.star_factory
 * @className:      redis
 * @author:     Eric
 * @description:  TODO  
 * @date:    2025/4/1 2:15
 * @version:    1.0
 */
@Configuration
@EnableCaching
public class RedisTemplateConfig {

    /**
     * write数据源
     */
    @Value("${spring.data.redis.host}")
    private String redisWriteHost;

    @Value("${spring.data.redis.port}")
    private int redisWritePort;

    @Value("${spring.data.redis.password}")
    private String redisWritePass;

    @Value("${spring.data.redis.database}")
    private int redisWriteDb;






    /**
     * 公共配置
     */
    @Value("${spring.data.redis.connect-timeout}")
    private long timeout;

    @Value("${spring.data.redis.lettuce.pool.min-idle}")
    private int minIdle;

    @Value("${spring.data.redis.lettuce.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.data.redis.lettuce.pool.max-active}")
    private int maxActive;

    @Value("${spring.data.redis.lettuce.pool.max-wait}")
    private int maxWait;


    /**
     * 装配 RedisTemplate
     */
//    @Bean(name = "redisTemplate")
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        return createRedisTemplate(redisConnectionFactory);
//    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        // 默认缓存配置（全局 TTL：三十分钟）
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofMinutes(30));

        // 构建 RedisCacheManager
        return RedisCacheManager.builder(factory)
                // 应用全局配置
                .cacheDefaults(defaultConfig)
                .build();
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }


    /**
     * 装配 StringRedisTemplate
     */
    @Bean(name = "writeStringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return createStringRedisTemplate(redisConnectionFactory);
    }

    /**
     * 装配 Write数据源
     */
//    @Bean(name = "writeStringRedisTemplate")
//    public StringRedisTemplate writeStringRedisTemplate() {
//        return createStringRedisTemplate(redisWriteHost, redisWritePort, redisWritePass, redisWriteDb);
//    }
    /**
     * 装配 Read数据源
     */


    /**
     * 创建 RedisTemplate
     */
    public RedisTemplate<Object, Object> createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        Jackson2JsonRedisSerializer<?> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 创建 StringRedisTemplate
     */
    public StringRedisTemplate createStringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }

    /**
     * 创建 StringRedisTemplate
     */
    public StringRedisTemplate createStringRedisTemplate(String host, int port, String password, int database) {
        // 基本配置
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(host);
        configuration.setPort(port);
        configuration.setDatabase(database);

        if (StrUtil.isNotBlank(password)) {
            RedisPassword redisPassword = RedisPassword.of(password);
            configuration.setPassword(redisPassword);
        }

        // 连接池通用配置
        GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);

        // Lettuce Pool
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
        builder.poolConfig(genericObjectPoolConfig);
        builder.commandTimeout(Duration.ofSeconds(timeout));
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration, builder.build());
        connectionFactory.afterPropertiesSet();

        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(connectionFactory);
        return stringRedisTemplate;
    }

}

