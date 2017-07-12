package com.test.cf.haash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HaashBrokerApplication {
	
	@Bean
    public Cloud cloud() {
        return new CloudFactory().getCloud();
    }


	public static void main(String[] args) {
		SpringApplication.run(HaashBrokerApplication.class, args);
	}
}
