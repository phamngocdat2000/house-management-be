package com.student.searchroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Configuration
public class SearchRoomApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchRoomApplication.class, args);
    }

}
