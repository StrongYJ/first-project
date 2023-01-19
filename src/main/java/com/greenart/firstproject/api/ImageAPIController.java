package com.greenart.firstproject.api;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import com.greenart.firstproject.util.FileStore;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class ImageAPIController {
    
    private final FileStore fileStore;

    @GetMapping("/product/{Imgname}")
    public ResponseEntity<Resource> getProductImages(@PathVariable("Imgname") String name) throws MalformedURLException {
        int endIndex = name.indexOf("_");
        String ext = name.substring(name.lastIndexOf("."));
        String downloadFilename = endIndex != -1 ? name.substring(0, endIndex) + Long.toString(System.currentTimeMillis()) : Long.toString(System.currentTimeMillis());
        String encodeDownFilname = UriUtils.encode(downloadFilename + ext, StandardCharsets.UTF_8);
        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION, 
                "attachment; filename=\"" + 
                encodeDownFilname
                + "\"")
            .body(
                new UrlResource("file:" + fileStore.getFullPath(name))
            );
            
    }
}
