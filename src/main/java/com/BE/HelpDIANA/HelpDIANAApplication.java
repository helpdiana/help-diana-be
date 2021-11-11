package com.BE.HelpDIANA;

import com.BE.HelpDIANA.test.SessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpSessionListener;

@SpringBootApplication
public class HelpDIANAApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelpDIANAApplication.class, args);
	}

	@Bean
	public HttpSessionListener httpSessionListener(){
		return new SessionListener();
	}

}
