package com.imanafrica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@ComponentScan
@EnableAutoConfiguration
@PropertySources(value = {
		@PropertySource(value = { "classpath:app.properties" })})
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
