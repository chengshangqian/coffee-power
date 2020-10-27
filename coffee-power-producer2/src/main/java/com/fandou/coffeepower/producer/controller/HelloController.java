package com.fandou.coffeepower.producer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${server.port}")
    private String serverPort;

    @Value("${spring.application.name}")
    private String applicationName;

    @RequestMapping("/hello")
    public String hello() {
        return String.format("{}:{} => Hello, Coffee!",applicationName,serverPort);
    }
}