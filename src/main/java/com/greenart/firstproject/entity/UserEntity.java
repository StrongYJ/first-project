package com.greenart.firstproject.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity(name = "user_info")
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ui_seq") private Long seq;
    @Column(name = "ui_name") private String name;
    @Column(name = "ui_email") private String email;
    @Column(name = "ui_pwd") private String pwd;
    @Column(name = "ui_nickname") private String nickname;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    @Column(name = "ui_birth") private Date birth;
    @Column(name = "ui_phone") private String phone;
    @Column(name = "ui_address") private String adress;
    @Column(name = "ui_status") private Integer status;
    @Column(name = "ui_reg_dt") private Date regDt;
}
