package com.electric_diary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ElectricDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectricDiaryApplication.class, args);
	}
}
