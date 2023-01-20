package com.example.w1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@RestController
public class W1Application {

	public static void main(String[] args) {
		SpringApplication.run(W1Application.class, args);
	}
	@GetMapping("/")
	public String sm(){
		return "Hello world";
	}
}
