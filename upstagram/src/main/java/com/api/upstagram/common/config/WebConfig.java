package com.api.upstagram.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Value("${cors.url}")
    private String allowOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        
        registry.addMapping("/**")
            .allowedOrigins(allowOrigins)
            .maxAge(3600)
            .allowCredentials(true);
    }
    
}
