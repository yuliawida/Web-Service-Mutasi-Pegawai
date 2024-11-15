package com.polstat.mutation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.polstat.mutation")
public class MutationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MutationApplication.class, args);
	}

}
