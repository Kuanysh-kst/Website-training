package com.example.demo.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class MainController {
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the unprotected page";
    }
    @GetMapping("hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/authenticated-user")
    public String authenticatedUser() {
        return "Hello authenticated user!";
    }

    @GetMapping("/login")
    public String login() {
        return "login-page";
    }

}
