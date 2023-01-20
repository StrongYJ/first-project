package com.greenart.firstproject.controller.adminsuper;

import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greenart.firstproject.util.FileStore;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/image")
@RequiredArgsConstructor
public class AdminImageController {
    
    private final FileStore fileStore;
    
    @ResponseBody
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws MalformedURLException {
        UrlResource urlResource = new UrlResource("file:" + fileStore.getFullPath(filename));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(urlResource);
    }
}
