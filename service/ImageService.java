package com.ecosystem.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageService {

    // ✅ ABSOLUTE PATH (VERY IMPORTANT)
    private final String path =
            "C:/Users/Paramveer Singh/IdeaProjects/backend/backend/images/";

    public String saveImage(
            MultipartFile file,
            String folder
    ) throws IOException {

        // ✅ CREATE FOLDER IF NOT EXISTS
        File dir = new File(path + folder);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // ✅ UNIQUE FILE NAME
        String fileName =
                System.currentTimeMillis()
                        + "_"
                        + file.getOriginalFilename();

        // ✅ SAVE FILE IN CORRECT LOCATION
        File saveFile = new File(dir, fileName);
        file.transferTo(saveFile);

        // ✅ RETURN PATH FOR FRONTEND
        return "images/" + folder + "/" + fileName;
    }
}