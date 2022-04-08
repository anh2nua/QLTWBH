package com.poly.assimentshop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.poly.assimentshop.config.StorageProperties;
import com.poly.assimentshop.service.StorageService;

import groovyjarjarpicocli.CommandLine;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class AssimentshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssimentshopApplication.class, args);
	}
@Bean
CommandLineRunner init(StorageService storageService) {
	return (args->{
		storageService.init();
	});
}
}
