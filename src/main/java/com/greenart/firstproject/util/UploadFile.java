package com.greenart.firstproject.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFile {
    private String originalFilename;
    private String storeFilename;
}
