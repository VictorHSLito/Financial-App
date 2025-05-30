package com.victor.financial_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FinancialAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialAppApplication.class, args);
	}

}
