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

@Getter
@Entity
@Table(name = "review_img")
public class ReviewImgEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rimg_seq") 
    private Long rimgSeq;

    @Column(name = "rimg_file_name") 
    private String rimgFileName;

    @JoinColumn(name = "rimg_ri_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private ReviewEntity review;

}
