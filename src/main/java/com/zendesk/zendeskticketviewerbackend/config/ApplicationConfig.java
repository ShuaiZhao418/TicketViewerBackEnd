package com.zendesk.zendeskticketviewerbackend.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public HttpClient getHttpClient() {
        return HttpClientBuilder.create().build();
    }
}
