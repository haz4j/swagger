package com.haz4j.swagger.config;


import com.haz4j.swagger.SwaggerController;
import com.haz4j.swagger.JsonGenerator;
import com.haz4j.swagger.SwaggerListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public SwaggerController swaggerController() {
        return new SwaggerController();
    }

    @Bean
    public JsonGenerator swaggerGenerator() {
        return new JsonGenerator();
    }

    @Bean
    public SwaggerListener swaggerListener() {
        return new SwaggerListener();
    }

}
