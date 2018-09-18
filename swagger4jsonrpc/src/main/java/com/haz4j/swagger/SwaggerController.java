package com.haz4j.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SwaggerController {

    public static final String JSON_URI = "/swagger.json";

    @Autowired
    private HttpServletRequest context;

    @Autowired
    private SwaggerListener listener;

    @GetMapping(path = JSON_URI)
    public String getSwaggerJson(){

        String url = context.getServerName()+":"+context.getServerPort()+context.getRequestURI();
        String host = url.substring(0, url.indexOf(JSON_URI));

        return listener.getJson().replace("host_placeholder", host);
    }
}
