package com.ecosystem.backend.service.impl;

import com.ecosystem.backend.dto.LoginRequest;
import com.ecosystem.backend.dto.RegisterRequest;
import com.ecosystem.backend.entity.Role;
import com.ecosystem.backend.entity.RoleName;
import com.ecosystem.backend.entity.User;
import com.ecosystem.backend.repository.RoleRepository;
import com.ecosystem.backend.repository.UserRepository;
import com.ecosystem.backend.security.JwtUtil;
import com.ecosystem.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest request) {

        // Create empty role set
        Set<Role> roleSet = new HashSet<>();

        // Loop through roles from request
        for (String roleStr : request.getRoles()) {

            RoleName roleName;

            try {
                roleName = RoleName.valueOf(
                        "ROLE_" + roleStr.trim().toUpperCase()
                );
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(
                        "Invalid role: " + roleStr
                );
            }

            Role role = roleRepository
                    .findByName(roleName)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Role not found: " + roleName
                            )
                    );

            roleSet.add(role);
        }

        // Create user
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRoles(roleSet);

        userRepository.save(user);
    }

    @Override
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // ✅ ADD THIS BLOCK
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );

        return jwtUtil.generateToken(userDetails);
    }
}