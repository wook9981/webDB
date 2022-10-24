package com.webdb.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan()
public class WebdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebdbApplication.class, args);
	}

}
