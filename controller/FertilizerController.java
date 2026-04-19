package com.ecosystem.backend.controller;

import com.ecosystem.backend.entity.Fertilizer;
import com.ecosystem.backend.service.FertilizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.
        PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fertilizer")
public class FertilizerController {

    @Autowired
    FertilizerService service;

    // BIOGAS + ADMIN
    @PostMapping
    @PreAuthorize("hasAnyRole('BIOGAS','ADMIN')")
    public Fertilizer add(
            @RequestBody Fertilizer f
    ) {
        System.out.println("ADD FERTILIZER CALLED");
        return service.save(f);
    }

    // ALL except service
    @GetMapping
    @PreAuthorize("hasAnyRole('FARMER','BIOGAS','ADMIN')")
    public List<Fertilizer> getAll() {
        return service.getAll();
    }

    // VIEW
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('FARMER','BIOGAS','ADMIN')")
    public Fertilizer getOne(
            @PathVariable Long id
    ) {
        return service.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('BIOGAS','ADMIN')")
    public Fertilizer update(
            @PathVariable Long id,
            @RequestBody Fertilizer f
    ) {
        f.setId(id);
        return service.save(f);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('BIOGAS','ADMIN')")
    public void delete(
            @PathVariable Long id
    ) {
        service.delete(id);
    }
}