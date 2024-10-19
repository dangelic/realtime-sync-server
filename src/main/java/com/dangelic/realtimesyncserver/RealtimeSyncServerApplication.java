package com.dangelic.realtimesyncserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dangelic")
public class RealtimeSyncServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(RealtimeSyncServerApplication.class, args);
	}
}
