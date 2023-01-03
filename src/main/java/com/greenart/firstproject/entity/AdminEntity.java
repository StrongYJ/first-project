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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admin")
@Getter @Setter
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
