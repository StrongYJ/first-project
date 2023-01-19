package com.greenart.firstproject.entity.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;

/**
 * 주종
 */

@Getter
@JsonFormat(shape = Shape.OBJECT)
public enum AlcoholType {
    TAKJU("takju", "탁주"), CHUNGJU("chungju" , "약·청주"), WINE("wine", "과실주"), SOJU("soju", "증류주");

    private final String code;
    private final String title;

    private AlcoholType(String code, String title) {
        this.code = code;
        this.title = title;
    }

    private static final Map<String, AlcoholType> BY_CODE = new ConcurrentHashMap<>();

    static {
        Arrays.stream(values()).forEach(v -> BY_CODE.put(v.code, v));
    }

    public static AlcoholType valueOfCode(String code) {
        return BY_CODE.get(code);
    }
}
