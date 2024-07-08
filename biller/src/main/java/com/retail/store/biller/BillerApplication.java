package com.retail.store.biller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.retail.store.biller"})
public class BillerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillerApplication.class, args);
	}

}
