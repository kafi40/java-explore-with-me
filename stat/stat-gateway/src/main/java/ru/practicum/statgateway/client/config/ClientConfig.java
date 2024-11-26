package ru.practicum.statgateway.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfig {

    @Bean
    public RestClient getRestClient(@Value("${stats-server.url}") String serverUrl) {
        return RestClient.create(serverUrl);
    }
}
