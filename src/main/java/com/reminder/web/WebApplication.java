package com.reminder.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ClassLoaderTemplateResolver loginTemplateResolver() {
        ClassLoaderTemplateResolver loginTemplateResolver = new ClassLoaderTemplateResolver();
        loginTemplateResolver.setPrefix("templates/login/");
        loginTemplateResolver.setSuffix(".html");
        loginTemplateResolver.setTemplateMode(TemplateMode.HTML);
        loginTemplateResolver.setCharacterEncoding("UTF-8");
        loginTemplateResolver.setOrder(1);
        loginTemplateResolver.setCheckExistence(true);
        return loginTemplateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver reminderTemplateResolver() {
        ClassLoaderTemplateResolver reminderTemplateResolver = new ClassLoaderTemplateResolver();
        reminderTemplateResolver.setPrefix("templates/reminder/");
        reminderTemplateResolver.setSuffix(".html");
        reminderTemplateResolver.setTemplateMode(TemplateMode.HTML);
        reminderTemplateResolver.setCharacterEncoding("UTF-8");
        reminderTemplateResolver.setOrder(2);
        reminderTemplateResolver.setCheckExistence(true);
        return reminderTemplateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver errorTemplateResolver() {
        ClassLoaderTemplateResolver errorTemplateResolver = new ClassLoaderTemplateResolver();
        errorTemplateResolver.setPrefix("templates/error/");
        errorTemplateResolver.setSuffix(".html");
        errorTemplateResolver.setTemplateMode(TemplateMode.HTML);
        errorTemplateResolver.setCharacterEncoding("UTF-8");
        errorTemplateResolver.setOrder(1);
        errorTemplateResolver.setCheckExistence(true);
        return errorTemplateResolver;
    }
}
