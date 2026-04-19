package com.ecosystem.backend.service;

import com.ecosystem.backend.dto.LoginRequest;
import com.ecosystem.backend.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);

    String login(LoginRequest request);

}