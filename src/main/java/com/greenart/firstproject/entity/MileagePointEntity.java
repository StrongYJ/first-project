package com.greenart.firstproject.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "mileage_point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MileagePointEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mp_seq")
    private Long mpSeq;

    @Column(name = "mp_price")
    private Integer mpPrice;

    @Column(name = "mp_expiration_date")
    private LocalDate mpExpirationDate;

    @JoinColumn(name = "mp_ui_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    public void changePrice(Integer mpPrice) {
        this.mpPrice = mpPrice;
    }

    @PrePersist
    private void setExpirationDate() {
        this.mpExpirationDate = LocalDate.now().plusYears(1L);
    }

    public MileagePointEntity(Integer mpPrice, UserEntity user) {
        this.mpPrice = mpPrice;
        this.user = user;
    }
}
