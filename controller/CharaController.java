package com.ecosystem.backend.controller;

import com.ecosystem.backend.entity.Chara;
import com.ecosystem.backend.repository.CharaRepository;
import com.ecosystem.backend.service.ImageService;
import com.ecosystem.backend.service.CharaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/chara")
public class CharaController {

    @Autowired
    CharaService service;

    @Autowired
    ImageService imageService;

    @Autowired
    CharaRepository charaRepository;


    // ✅ FARMER can sell chara
    @PostMapping
    @PreAuthorize("hasAnyRole('FARMER','ADMIN')")
    public Chara add(
            @RequestBody Chara c
    ) {
        return service.save(c);
    }


    // ✅ All can view except service role
    @GetMapping
    @PreAuthorize("hasAnyRole('SUPPLIER','FARMER','ADMIN')")
    public List<Chara> getAll() {
        return service.getAll();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPPLIER','FARMER','ADMIN')")
    public Chara getOne(
            @PathVariable Long id
    ) {
        return service.getById(id);
    }


    // ✅ Only farmer delete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('FARMER','ADMIN')")
    public void delete(
            @PathVariable Long id
    ) {
        service.delete(id);
    }


    // ✅ Upload with image (same as waste)
    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('FARMER','ADMIN')")
    public Chara addChara(

            @RequestParam String name,
            @RequestParam String type,
            @RequestParam double quantity,
            @RequestParam double price,
            @RequestParam String location,
            @RequestParam MultipartFile image

    ) throws Exception {

        Chara c = new Chara();

        c.setName(name);
        c.setType(type);
        c.setQuantity(quantity);
        c.setPrice(price);
        c.setLocation(location);

        String path =
                imageService.saveImage(
                        image,
                        "chara"
                );

        c.setImageUrl(path);

        return charaRepository.save(c);
    }
}