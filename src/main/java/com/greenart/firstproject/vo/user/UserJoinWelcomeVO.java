package com.greenart.firstproject.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserJoinWelcomeVO {
    private Long seq;
    private String name;
    private String nickname;
}
