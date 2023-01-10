package com.greenart.firstproject.vo.superadmin;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminUserVO {
    
    private Long seq;
    private String name;
    private String email;
    private String nickname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    
    private String phone;
    private String address;
    private Integer status;
    private String statusToString;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime regDt;

    public AdminUserVO(Long seq, String name, String email, String nickname, LocalDate birth, String phone,
            String address, Integer status, LocalDateTime regDt) {
        this.seq = seq;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.statusToString = switch(status) {
            case 2 -> "정지";
            case 3 -> "탈퇴대기";
            default -> "정상";
        };  // 상태(1:정상, 2:정지, 3:탈퇴대기)
        this.regDt = regDt;
    }
}
