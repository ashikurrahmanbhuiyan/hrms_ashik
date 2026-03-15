package com.example.hrms_ashik;

import org.springframework.stereotype.Component;

@Component
public class Schedule {

//    @Scheduled(fixedRate = 1000) // 60000 ms = 1 minute
    public void runEveryMinute() {
        System.out.println("Running task every 1 minute: " + System.currentTimeMillis());
    }
}