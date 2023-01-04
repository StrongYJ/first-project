package com.greenart.firstproject.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
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

    // public UserEntity fromCreateUserVO(CreateUserVO data) {
    //     UserEntity newUser = new UserEntity();
    //     newUser.setEmail(data.getEmail());
    //     return newUser;
    // }
}
