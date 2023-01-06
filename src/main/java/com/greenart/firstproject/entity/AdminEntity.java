package com.greenart.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admin")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminEntity {
    
    @Column(name = "admin_seq")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    
    @Column(name = "admin_id")
    private String id;
    
    @Column(name = "admin_pwd")
    private String pwd;
    
    @Column(name = "admin_grade")
    private Integer grade;
    
    @JoinColumn(name = "admin_mi_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private MarketInfoEntity marketInfo;

}
