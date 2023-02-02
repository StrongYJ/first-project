package com.greenart.firstproject.yeongjun.vo;

import com.greenart.firstproject.vo.user.UserJoinVO;

public record UserJoinVOTest(String name, String email, String pwd, String nickname, String birth, String phone, String address) {
    public UserJoinVOTest(UserJoinVO joinVO) {
        this(joinVO.getName(), joinVO.getEmail(), joinVO.getPwd(), joinVO.getNickname(), joinVO.getBirth().toString(), joinVO.getPhone(), joinVO.getAddress());
    }
}
