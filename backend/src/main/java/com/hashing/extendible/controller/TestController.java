package com.hashing.extendible.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }

    @GetMapping("/test")
    public String test(){
        return "yes, the extendible hashing visualizer backend is working!";
    }
}
