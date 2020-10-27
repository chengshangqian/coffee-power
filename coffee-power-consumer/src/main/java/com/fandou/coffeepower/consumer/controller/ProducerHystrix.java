package com.fandou.coffeepower.consumer.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class ProducerHystrix implements ProducerService {

    @RequestMapping("/hello")
    public String hello() {
    	return "sorry, hello service call failed.";
    }
}
