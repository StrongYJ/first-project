package com.greenart.firstproject.entity;

import com.greenart.firstproject.vo.review.ReviewCreateVO;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "review_img")
@NoArgsConstructor(access = AccessLevel. PROTECTED)
public class ReviewImgEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rimg_seq") 
    private Long rimgSeq;

    @Column(name = "rimg_file_name") 
    private String rimgFileName;

    @JoinColumn(name = "rimg_ri_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private ReviewEntity review;

    @Builder
    public ReviewImgEntity(String imgName, ReviewEntity reviewEntity){
        this.rimgFileName = imgName;
        this.review = reviewEntity;
    }
}
