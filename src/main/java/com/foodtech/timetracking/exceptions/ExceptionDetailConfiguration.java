package com.foodtech.timetracking.exceptions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionDetailConfiguration {

    @Bean
    public ExceptionDetails basicExceptionInformation() {
        return new BasicExceptionDetails();
    }
}
