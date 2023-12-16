package com.zerobase.zbcharger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ZbchargerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZbchargerApplication.class, args);
	}

}
