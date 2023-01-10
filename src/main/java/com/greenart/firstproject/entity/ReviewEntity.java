package com.greenart.firstproject.entity;

import java.time.LocalDateTime;

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

@Getter
@Entity
@Table(name = "review_info")
public class ReviewEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ri_seq") 
    private Long seq;

    @Column(name = "ri_grade") 
    private Double grade;

    @Column(name = "ri_content") 
    private String content;

    @Column(name = "ri_reg_dt") 
    private LocalDateTime regDt;

    @JoinColumn(name = "ri_ui_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @JoinColumn(name = "ri_oi_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private OptionInfoEntity option;
    
}
