package com.greenart.firstproject.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.greenart.firstproject.vo.user.UserJoinVO;
import com.greenart.firstproject.vo.user.UserUpdateVO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_info")
public class UserEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ui_seq") 
    private Long seq;

    @Column(name = "ui_name")
    private String name;

    @Column(name = "ui_email") 
    private String email;

    @Column(name = "ui_pwd") 
    private String pwd;

    @Column(name = "ui_nickname") 
    private String nickname;

    @Column(name = "ui_birth") 
    private LocalDate birth;

    @Column(name = "ui_phone") 
    private String phone;

    @Column(name = "ui_address") 
    private String address;

    @Column(name = "ui_status") 
    private Integer status;

    @Column(name = "ui_reg_dt") 
    private LocalDateTime regDt;

    @Builder
    public UserEntity(UserJoinVO data) {
        this.name = data.getName();
        this.email = data.getEmail();
        this.pwd = data.getPwd();
        this.nickname = data.getNickname();
        this.birth = data.getBirth();
        this.phone = data.getPhone();
        this.address = data.getAddress();
        this.status = 1;
        this.regDt = LocalDateTime.now();
    }

    public void updateUser(UserUpdateVO data) {
        this.pwd = data.getPwd();
        this.nickname = data.getNickname();
        this.address = data.getAddress();
    }

    public void changeStatus(Integer status) {
        this.status = status;
    }
}
