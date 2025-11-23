package com.example.patient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8888") // 设置基础 URL，避免在每个请求中重复
                .defaultHeader("User-Agent", "MyApp/1.0") // 设置默认请求头
                // 可以在这里配置超时 (需要引入 reactor-netty)
                // .clientConnector(new ReactorClientHttpConnector(HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)))
                .build();
    }
}
