package com.example.finalProject.controller;

import com.example.finalProject.config.FastApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FastApiController {
    @Autowired
    private FastApiConfig fastApiConfig;

    @GetMapping("/api/fastapi-endpoints")
    public Map<String, String> getFastApiEndpoints() {
        return Map.of(
                "url", fastApiConfig.getFastApiUrl(),
                "processUserData", fastApiConfig.getProcessUserDataEndpoint()
        );
    }
}