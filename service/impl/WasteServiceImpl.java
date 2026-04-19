package com.ecosystem.backend.service.impl;

import com.ecosystem.backend.entity.User;
import com.ecosystem.backend.entity.Waste;
import com.ecosystem.backend.repository.UserRepository;
import com.ecosystem.backend.repository.WasteRepository;
import com.ecosystem.backend.service.WasteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class WasteServiceImpl implements WasteService {

    @Autowired
    WasteRepository repo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public Waste save(Waste waste) {

        String email = request.getUserPrincipal().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 IF UPDATE
        if (waste.getId() != null) {

            Waste existing = repo.findById(waste.getId())
                    .orElseThrow(() -> new RuntimeException("Waste not found"));

            // ❌ BLOCK OTHER USERS
            if (!existing.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Unauthorized ❌");
            }
        }

        waste.setUser(user);

        return repo.save(waste);
    }

    @Override
    public List<Waste> getAll() {

        String email = request.getUserPrincipal().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return repo.findByUser(user); // ✅ only current user data
    }

    @Override
    public Waste getById(Long id) {

        String email = request.getUserPrincipal().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Waste waste = repo.findById(id).orElseThrow();

        if (!waste.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized ❌");
        }

        return waste;
    }

    @Override
    public void delete(Long id) {

        String email = request.getUserPrincipal().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Waste waste = repo.findById(id).orElseThrow();

        if (!waste.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized ❌");
        }

        repo.deleteById(id);
    }
}