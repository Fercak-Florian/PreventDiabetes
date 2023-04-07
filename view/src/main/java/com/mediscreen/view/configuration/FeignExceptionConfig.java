package com.mediscreen.view.configuration;

import com.mediscreen.view.exception.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignExceptionConfig {

    @Bean
    CustomErrorDecoder customErrorDecoder(){
        return new CustomErrorDecoder();
    }
}
