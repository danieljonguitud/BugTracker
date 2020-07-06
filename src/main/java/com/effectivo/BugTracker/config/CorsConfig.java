package com.effectivo.BugTracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8081",
                                "http://bug-tracker.nagisando.com",
                                "http://bug-tracker.danieljonguitud.com")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");;
            }
        };
    }
}