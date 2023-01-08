package com.greenart.firstproject.controller.adminsuper;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greenart.firstproject.config.FilePath;

@Controller
@RequestMapping("/admin/image")
public class AdminImageController {
    
    @ResponseBody
    @GetMapping("/{filename}")
    public Resource getImage(@PathVariable String filename) throws MalformedURLException {
        Path imagePath = Paths.get(FilePath.PRODUCT_IMAGES).resolve(filename);
        return new UrlResource(imagePath.toUri());
    }
}
