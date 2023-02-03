package com.greenart.firstproject.vo.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateVO {
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$") // 8~20 영문+숫자+특수문자
    private String pwd;

    private String nickname;
    private String address;
    
    public UserUpdateVO(String pwd, String nickname, String address) {
        this.pwd = pwd;
        this.nickname = nickname;
        this.address = address;
    }
}
