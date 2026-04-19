package com.ecosystem.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private List<String> roles;

}