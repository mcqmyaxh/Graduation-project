package com.example.patient;

import com.visualization.cloud.visualization;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@MapperScan( "com.example.kkhourse.*.mapper")
//@ComponentScan(basePackages = {"com.visualization.cloud.quartz","com.example"})
@ComponentScan(basePackages = {"com.example"})
@ConfigurationPropertiesScan
//@Import({RocketMQAutoConfiguration.class})
@Import({RocketMQAutoConfiguration.class,
        visualization.class
})
@EnableCaching
@EnableAsync
public class PatientApplication {
    public static void main(String[] args) {
        SpringApplication.run(PatientApplication.class, args);
    }

}
