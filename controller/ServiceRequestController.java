package com.ecosystem.backend.controller;

import com.ecosystem.backend.entity.*;
import com.ecosystem.backend.service.ServiceRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import com.ecosystem.backend.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ServiceRequestController {

    @Autowired
    ServiceRequestService service;

    @Autowired
    UserRepository userRepository;

    // =============================
    // CREATE SERVICE REQUEST
    // =============================
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPPLIER','BIOGAS','FARMER','ADMIN')")
    public ServiceRequest add(
            @RequestBody ServiceRequest s,
            Authentication auth
    ) {

        String email = auth.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        s.setUserId(user.getId());
        s.setStatus(ServiceStatus.PENDING);

        return service.save(s);
    }

    // =============================
    // GET ALL (ROLE BASED)
    // =============================
    @GetMapping
    @PreAuthorize("hasAnyRole('SUPPLIER','BIOGAS','FARMER','SERVICE','ADMIN')")
    public List<ServiceRequest> getAll(Authentication auth) {

        String email = auth.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        String role = auth.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        if (role.equals("ROLE_ADMIN")) {
            return service.getAll();
        }

        if (role.equals("ROLE_SERVICE")) {
            return service.getActiveForService();
        }

        return service.getByUserId(user.getId());
    }

    // =============================
    // ACCEPT SERVICE
    // =============================
    @PutMapping("/accept/{id}")
    @PreAuthorize("hasRole('SERVICE')")
    public ServiceRequest accept(
            @PathVariable Long id,
            Authentication auth
    ) {

        String email = auth.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        ServiceRequest s = service.getById(id);

        if (s.getAcceptedByUserId() != null) {
            throw new RuntimeException("Already accepted");
        }

        if (s.getStatus() != ServiceStatus.PENDING) {
            throw new RuntimeException("Only PENDING service can be accepted");
        }

        s.setStatus(ServiceStatus.ACCEPTED);
        s.setAcceptedByUserId(user.getId());

        return service.save(s);
    }

    // =============================
    // REJECT SERVICE
    // =============================
    @PutMapping("/reject/{id}")
    @PreAuthorize("hasAnyRole('SERVICE','ADMIN')")
    public ServiceRequest reject(@PathVariable Long id) {

        ServiceRequest s = service.getById(id);

        if (s.getStatus() != ServiceStatus.PENDING) {
            throw new RuntimeException("Only PENDING service can be rejected");
        }

        s.setStatus(ServiceStatus.REJECTED);

        return service.save(s);
    }

    // =============================
    // START SERVICE
    // =============================
    @PutMapping("/start/{id}")
    @PreAuthorize("hasAnyRole('SERVICE','ADMIN')")
    public ServiceRequest startService(
            @PathVariable Long id,
            Authentication auth
    ) {

        String email = auth.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        ServiceRequest s = service.getById(id);

        if (!user.getId().equals(s.getAcceptedByUserId())) {
            throw new RuntimeException("You are not allowed to start this service");
        }

        if (s.getStatus() != ServiceStatus.ACCEPTED) {
            throw new RuntimeException("Service must be ACCEPTED before starting");
        }

        s.setStatus(ServiceStatus.IN_PROGRESS);
        s.setStartedByUserId(user.getId());

        return service.save(s);
    }

    // =============================
    // COMPLETE SERVICE
    // =============================
    @PutMapping("/complete/{id}")
    @PreAuthorize("hasAnyRole('SERVICE','ADMIN')")
    public ServiceRequest completeService(
            @PathVariable Long id,
            Authentication auth
    ) {

        String email = auth.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        ServiceRequest s = service.getById(id);

        if (!user.getId().equals(s.getAcceptedByUserId())) {
            throw new RuntimeException("You are not allowed to complete this service");
        }

        if (s.getStatus() != ServiceStatus.IN_PROGRESS) {
            throw new RuntimeException("Service must be IN_PROGRESS before completion");
        }

        s.setStatus(ServiceStatus.COMPLETED);
        s.setCompletedByUserId(user.getId());

        return service.save(s);
    }

    // =============================
    // CANCEL SERVICE (NEW FEATURE)
    // =============================
    @PutMapping("/cancel/{id}")
    @PreAuthorize("hasAnyRole('SUPPLIER','BIOGAS','FARMER')")
    public ServiceRequest cancel(
            @PathVariable Long id,
            Authentication auth
    ) {

        String email = auth.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        ServiceRequest s = service.getById(id);

        if (!s.getUserId().equals(user.getId())) {
            throw new RuntimeException("Not your service");
        }

        // allow cancel in PENDING & ACCEPTED only
        if (s.getStatus() != ServiceStatus.PENDING &&
                s.getStatus() != ServiceStatus.ACCEPTED) {

            throw new RuntimeException(
                    "You can only cancel PENDING or ACCEPTED service"
            );
        }

        // block if already started
        if (s.getStartedByUserId() != null) {
            throw new RuntimeException("Service already started, cannot cancel");
        }

        s.setStatus(ServiceStatus.CANCELLED);

        return service.save(s);
    }

    // =============================
    // USER LOG
    // =============================
    @GetMapping("/my-log")
    @PreAuthorize("hasAnyRole('SUPPLIER','BIOGAS','FARMER','ADMIN')")
    public List<ServiceRequest> myLog(Authentication auth) {

        String email = auth.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        return service.getByUserId(user.getId());
    }

    // =============================
    // DELETE LOG (SAFE)
    // =============================
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('SUPPLIER','BIOGAS','FARMER','ADMIN')")
    public void deleteLog(
            @PathVariable Long id,
            Authentication auth
    ) {

        String email = auth.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        ServiceRequest s = service.getById(id);

        if (!s.getUserId().equals(user.getId())) {
            throw new RuntimeException("Not your log");
        }

        if (s.getStatus() == ServiceStatus.IN_PROGRESS ||
                s.getStatus() == ServiceStatus.COMPLETED ||
                s.getStatus() == ServiceStatus.ACCEPTED) {

            throw new RuntimeException(
                    "Cannot delete after service is accepted or started"
            );
        }

        service.delete(id);
    }

    // =============================
    // SERVICE USER LOG (ALL)
    // =============================
    @GetMapping("/service_log")
    @PreAuthorize("hasAnyRole('SERVICE','ADMIN')")
    public List<ServiceRequest> serviceLogs(Authentication auth) {

        String email = auth.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        return service.getByAcceptedUser(user.getId());
    }

    // =============================
    // FILTER BY STATUS
    // =============================
    @GetMapping("/service_log/{status}")
    @PreAuthorize("hasAnyRole('SERVICE','ADMIN')")
    public List<ServiceRequest> filterByStatus(
            @PathVariable ServiceStatus status,
            Authentication auth
    ) {

        String email = auth.getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow();

        return service.getByAcceptedUserAndStatus(
                user.getId(),
                status
        );
    }
}