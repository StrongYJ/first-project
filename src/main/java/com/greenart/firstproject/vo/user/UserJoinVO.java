package com.greenart.firstproject.vo.user;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJoinVO {
    @NotBlank
    private String name;
    
    @NotNull
    @Email
    private String email;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$") // 8~20 영문+숫자+특수문자
    private String pwd;

    @NotBlank
    private String nickname;

    @Past
    private LocalDate birth;

    @Pattern(regexp = "^010(\\d{4})(\\d{4})$") //010XXXXOOOO
    private String phone;

    @NotBlank
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
