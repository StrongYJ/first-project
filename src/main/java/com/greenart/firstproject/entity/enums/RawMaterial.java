package com.greenart.firstproject.entity.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;

/**
 * 술 원료
 */
@Getter
@JsonFormat(shape = Shape.OBJECT)
public enum RawMaterial {
    FRUIT_VEG("fruit", "과채류"), FLOWER("flower", "꽃"), GRAIN_NUT("grain-nut", "곡물견과류"), HERB("herb", "약재"), ETC("etc", "기타");

    private final String code;
    private final String title;
    
    private RawMaterial(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    private static final Map<String, RawMaterial> BY_CODE = new ConcurrentHashMap<>();

    static {
        Arrays.stream(values()).forEach(v -> BY_CODE.put(v.code, v));
    }

    public static RawMaterial valueOfCode(String code) {
        return BY_CODE.get(code);
    }
}
