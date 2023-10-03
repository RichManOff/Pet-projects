package com.example.provence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProvenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProvenceApplication.class, args);
    }

}
