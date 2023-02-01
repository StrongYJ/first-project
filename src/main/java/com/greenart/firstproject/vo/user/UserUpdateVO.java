package com.greenart.firstproject.vo.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor()
public class UserUpdateVO {
    private String pwd;
    private String nickname;
    private String address;

    public UserUpdateVO(String pwd, String nickname, String address) {
        this.pwd = pwd;
        this.nickname = nickname;
        this.address = address;
    }
}
