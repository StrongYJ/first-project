package com.greenart.firstproject.util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileStore {
    
    @Value("${file.dir}")
    private String fileDir;

    public Path getFullPath(String filename) {
        return Paths.get(fileDir).resolve(filename);
    }

    /**
     * 
     * @param fileGroup 파일이 쓰이는 서비스 ex) 리뷰서비스 -> review 제품 대표이미지 -> productImg
     * @param multipartFiles
     * @return
     */
    public List<UploadFile> storeFiles(String fileGroup, MultipartFile ...multipartFiles) {
        List<UploadFile> storefiles = new ArrayList<>();
        Arrays.stream(multipartFiles).filter(f -> !f.isEmpty()).forEach(f -> 
            storefiles.add(storeSingleFile(fileGroup, f))
        );
        return storefiles;
    }

    /**
     * 
     * @param fileGroup 파일이 쓰이는 서비스 ex) 리뷰서비스 -> review 제품 대표이미지 -> productImg
     * @param multipartFile
     * @return
     */
    public UploadFile storeSingleFile(String fileGroup, MultipartFile multipartFile) {
        if(multipartFile.isEmpty()) return null;
        
        String originalFilename = multipartFile.getOriginalFilename();
        String storedFilename = createFileStoreName(fileGroup, originalFilename);
        try {
            multipartFile.transferTo(getFullPath(storedFilename));
        } catch (IllegalStateException | IOException e) {
            log.error("fileStoreError = {}", e.getMessage());
            e.printStackTrace();
        }

        return new UploadFile(originalFilename, storedFilename);
    }

    private String createFileStoreName(String prefix, String originalFilename) {
        return prefix + "_" + UUID.randomUUID().toString() + "." + extractExt(originalFilename);
    }

    private String extractExt(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

}
