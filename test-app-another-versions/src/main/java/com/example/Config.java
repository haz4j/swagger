package com.example;


import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import com.haz4j.swagger.Docket;
import com.haz4j.swagger.annotation.EnableSwagger4JsonRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableSwagger4JsonRpc
public class Config {

    @Bean
    public Docket docket(){
        return Docket.builder().build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Config.class, args);
    }

    @Bean
    @Autowired
    public static AutoJsonRpcServiceImplExporter autoJsonRpcServiceImplExporter() {
        return new AutoJsonRpcServiceImplExporter();
    }
}