package com.javarush.reactflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    @Bean
    public RestClient getRestClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:24130/api/v1.0/reactions")
                .build();
    }
}
