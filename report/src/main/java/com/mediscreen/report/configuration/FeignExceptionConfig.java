package com.mediscreen.report.configuration;

import com.mediscreen.report.exception.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignExceptionConfig {
    @Bean
    CustomErrorDecoder customErrorDecoder(){
        return new CustomErrorDecoder();
    }
}
