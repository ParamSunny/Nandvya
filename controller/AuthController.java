package com.ecosystem.backend.controller;

import com.ecosystem.backend.dto.LoginRequest;
import com.ecosystem.backend.dto.RegisterRequest;
import com.ecosystem.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request
    ) {
        authService.register(request);
        return "REGISTERED";
    }

    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }
}