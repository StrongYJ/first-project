package com.greenart.firstproject.entity.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;

@Getter
@JsonFormat(shape = Shape.OBJECT)
public enum LevelRangeCode {
    
    LOW("low"), MIDDLE("middle"), HIGH("high"), VERY_HIGH("very-high");

    private String code;
    private LevelRangeCode(String code) {
        this.code = code;
    }

    private static final Map<String, LevelRangeCode> BY_CODE = new ConcurrentHashMap<>();

    static {
        Arrays.stream(values()).forEach(v -> BY_CODE.put(v.code, v));
    }

    public static LevelRangeCode valueOfCode(String code) {
        return BY_CODE.get(code);
    }
    
}
