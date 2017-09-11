package com.stelinno.cloud.bluemix;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.google.gson.Gson;
import com.stelinno.http.HTTPHelper;
import com.stelinno.http.StandardHTTPHelper;

@Configuration
@ComponentScan("com.stelinno.cloud.bluemix")
public class AppConfig {

	@Bean String version() { return "V1.0.2017-08-09-16:11"; }	
	@Bean Gson gson() { return new Gson(); }
	@Bean ObjectPersistenceService objPersistenceService() { return new COSPersistenceService(); }
	@Bean DocumentPersistenceService docPersistenceService() { return new CloudantPersistenceService(); }
	@Bean HTTPHelper httpHelper() { return new StandardHTTPHelper(); }
	@Bean String cloudantDBUrl() { return "https://0ffd881d-30b5-42a7-aee9-7140b4c257c4-bluemix.cloudant.com/football-results"; };
	@Bean FootballResultsCrawler footballResultsCrawler() { return new  BoldDKFootballResultsCrawler(); }
}
