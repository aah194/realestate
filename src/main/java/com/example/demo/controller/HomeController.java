package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
    @GetMapping("/contact")
    public String cantact() {
        return "contact-us";
    }
    @GetMapping("/about")
    public String about() {
        return "about-us";
    }
    
}
