package com.greenart.firstproject.vo.user;

import java.time.LocalDate;

import com.greenart.firstproject.entity.UserEntity;

public record UserResponseVO(String name, String email, String nickname, LocalDate birth, String phone, String address) {
    public UserResponseVO(UserEntity entity) {
        this(entity.getName(), entity.getEmail(), entity.getNickname(), entity.getBirth(), entity.getPhone(),
                entity.getAddress());
    }
}
