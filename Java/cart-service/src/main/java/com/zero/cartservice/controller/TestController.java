package com.zero.cartservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class TestController {

    @Autowired

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Service is up and running!");
    }
}
