package com.backend.users;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TestConnectionController {

    @GetMapping
    public ResponseEntity<String> testConnection() {
        return ResponseEntity.ok("Hello World");
    }
}
