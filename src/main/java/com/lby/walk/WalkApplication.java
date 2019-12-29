package com.lby.walk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class WalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalkApplication.class, args);
	}

}
