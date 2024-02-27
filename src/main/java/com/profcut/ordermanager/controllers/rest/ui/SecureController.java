package com.profcut.ordermanager.controllers.rest.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/secure")
public class SecureController {


    @GetMapping
    private ResponseEntity<String> getHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }
}
