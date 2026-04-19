package com.ecosystem.backend.controller;

import com.ecosystem.backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload/waste")
    public String uploadWasteImage(
            @RequestParam("image") MultipartFile image
    ) throws Exception {

        return imageService.saveImage(image, "waste");
    }
}