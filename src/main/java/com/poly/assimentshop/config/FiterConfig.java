package com.poly.assimentshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.poly.assimentshop.Filter.Filter;

@Configuration
public class FiterConfig implements WebMvcConfigurer{
@Autowired
private Filter filter;	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(filter).addPathPatterns("/admin/**");
	}

}
