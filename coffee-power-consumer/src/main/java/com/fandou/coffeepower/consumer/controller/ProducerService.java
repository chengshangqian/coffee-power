package com.fandou.coffeepower.consumer.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "coffee-power-producer", fallback = ProducerHystrix.class)
public interface ProducerService {

    @RequestMapping("/hello")
    public String hello();
    
}
