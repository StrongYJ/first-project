package com.greenart.firstproject.vo;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJoinVO {
    private String name;
    private String email;
    private String pwd;
    private String nickname;
    private LocalDate birth;
    private String phone;
    private String address;

    public UserJoinVO(String name, String email, String pwd, String nickname, LocalDate birth, String phone,
            String address) {
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
    }
}
