package com.imanafrica.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	 @Bean
	 public ViewResolver viewResolver() {
	
	 ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	 viewResolver.setTemplateEngine(templateEngine());
	 viewResolver.setOrder(1);
	 viewResolver.setViewNames(new String[] { "*" });
	 viewResolver.setCache(false);
	 return viewResolver;
	 }
	
	 public SpringTemplateEngine templateEngine() {
	 SpringTemplateEngine engine = new SpringTemplateEngine();
	 engine.setTemplateResolver(templateResolver());
	 return engine;
	 }
	
	 @Bean
	 public ServletContextTemplateResolver templateResolver() {
	 ServletContextTemplateResolver resolver = new
	 ServletContextTemplateResolver();
	 resolver.setPrefix("/resources/views/");
	 resolver.setSuffix(".html");
	 // NB, selecting HTML5 as the template mode.
	 resolver.setTemplateMode("HTML5");
	 resolver.setCacheable(false);
	 return resolver;
	
	 }

	@Bean
	MultipartConfigElement multipartConfigElement() {
		return new MultipartConfigElement("");
	}
	
	@Bean
	public MessageSource messageSource() {

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages/messages", "classpath:messages/validation");
		// if true, the key of the message will be displayed if the key is not
		// found, instead of throwing a NoSuchMessageException
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		// # -1 : never reload, 0 always reload
		messageSource.setCacheSeconds(0);
		return messageSource;
	}

}
