package com.diana;

import com.diana.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class Help_DIANA {

	public static void main(String[] args) {
		SpringApplication.run(Help_DIANA.class, args);
	}
}
