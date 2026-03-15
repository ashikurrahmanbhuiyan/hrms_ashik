package com.example.hrms_ashik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HrmsAshikApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrmsAshikApplication.class, args);
	}

}
