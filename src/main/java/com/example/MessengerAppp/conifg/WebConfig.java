package com.example.MessengerAppp.conifg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4200","https://senniamohamed.netlify.app/","https://trackprices.netlify.app/","null")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
        registry.addMapping("/messages/**   ")
                .allowedOrigins("http://localhost:4200","https://senniamohamed.netlify.app/","https://trackprices.netlify.app/","null")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
        registry.addMapping("**   ")
                .allowedOrigins("http://localhost:4200","https://senniamohamed.netlify.app/","https://trackprices.netlify.app/","null")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }

}
