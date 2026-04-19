package com.ecosystem.backend.controller;

import com.ecosystem.backend.entity.Waste;
import com.ecosystem.backend.service.ImageService;
import com.ecosystem.backend.service.WasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/waste")
public class WasteController {

    @Autowired
    WasteService service;

    @Autowired
    ImageService imageService;

    // ✅ ADD WASTE
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_SUPPLIER','ROLE_ADMIN')")
    public Waste add(
            @RequestBody Waste waste,
            Principal principal
    ) {
        return service.save(waste);
    }

    // ✅ GET ONLY MY DATA
    @GetMapping
    @PreAuthorize("hasAnyRole('SUPPLIER','BIOGAS','ADMIN')")
    public List<Waste> getAll() {
        return service.getAll();
    }

    // ✅ GET ONE (SECURE)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPPLIER','BIOGAS','ADMIN')")
    public Waste getOne(
            @PathVariable Long id,
            Principal principal
    ) {
        return service.getById(id);
    }


    // ✅ DELETE (ONLY OWNER)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPPLIER','ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id); // ✅ correct
    }

    // ✅ UPDATE (ONLY OWNER)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPPLIER','ADMIN')")
    public Waste update(
            @PathVariable Long id,
            @RequestBody Waste waste,
            Principal principal
    ) {
        waste.setId(id);
        return service.save(waste);
    }

    // ✅ UPLOAD WITH IMAGE
    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPPLIER','ROLE_ADMIN')")
    public Waste addWaste(
            @RequestParam String name,
            @RequestParam String type,
            @RequestParam double quantity,
            @RequestParam double price,
            @RequestParam String location,
            @RequestParam("image") MultipartFile image
    ) throws Exception {

        Waste w = new Waste();

        w.setName(name);
        w.setType(type);
        w.setQuantity(quantity);
        w.setPrice(price);
        w.setLocation(location);

        // 🔥 SAVE IMAGE
        String path = imageService.saveImage(image, "waste");
        w.setImageUrl(path);

        // 🔥 SAVE WITH USER
        return service.save(w);
    }
}