package com.iamjrp.cowin.config;

import javax.servlet.Filter;

import com.moesif.servlet.MoesifFilter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.*;

@Configuration
public class APITrafficConfig extends WebMvcConfigurerAdapter {

  @Bean
  public Filter moesifFilter() {
    return new MoesifFilter("eyJhcHAiOiIzNDU6OTgwIiwidmVyIjoiMi4wIiwib3JnIjoiMjQwOjQxNCIsImlhdCI6MTYxOTgyNzIwMH0.Li7nzlqFeyX6vCABPwycZTsUeQwoih5asvmJwEfW4BE");
  }
}