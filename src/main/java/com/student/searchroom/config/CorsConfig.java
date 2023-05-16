package com.student.searchroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Cho phép truy cập từ tất cả các đường dẫn
                        .allowedOrigins("http://localhost:3000", "http://localhost:63342") // Cấp quyền truy cập từ nguồn gốc 'http://localhost:3000'
                        .allowedMethods("*") // Cho phép sử dụng tất cả các phương thức (GET, POST, PUT, DELETE, ...)
                        .allowedHeaders("*"); // Cho phép sử dụng tất cả các headers
            }
        };
    }

}
