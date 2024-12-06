package com.example.finalProject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FastApiConfig {
    @Value("${fastapi.url}")
    private String fastApiUrl;

    @Value("${fastapi.api.process-user-data}")
    private String processUserDataEndpoint;

    public String getFastApiUrl() {
        return fastApiUrl;
    }

    public String getProcessUserDataEndpoint() {
        return processUserDataEndpoint;
    }
}