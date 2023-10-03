package com.example.provence.configuration;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyScheduledTask {

    @Scheduled(fixedRate = 13 * 60 * 1000) // Run every 13 minutes
    public void performTask() {
        // Your code to send requests goes here
        System.out.println("Request sent at: " + new Date());
    }
}
