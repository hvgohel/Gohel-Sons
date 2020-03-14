package com.gohel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GohelAndSonsApplication implements WebMvcConfigurer {

  public static void main(String[] args) {
    SpringApplication.run(GohelAndSonsApplication.class, args);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("dashboard");
    registry.addViewController("/login").setViewName("login");
  }
}
