package com.greenart.firstproject.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {
    private Object status;
    private String message;
}
