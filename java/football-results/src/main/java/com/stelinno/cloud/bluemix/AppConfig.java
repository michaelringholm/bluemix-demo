package com.stelinno.cloud.bluemix;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.google.gson.Gson;

@Configuration
@ComponentScan("com.stelinno.cloud.bluemix")
public class AppConfig {

	@Bean String version() {
		System.out.println("called version()!");
		return "V1.0.2017-08-09-16:11";
	}
}
