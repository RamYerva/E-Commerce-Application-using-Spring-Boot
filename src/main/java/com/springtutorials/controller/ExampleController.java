package com.springtutorials.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/example")
public class ExampleController {

    @GetMapping
    public String helloExample() {
        return "Hello from Example API!";
    }
}

