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
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "mileage_point")
public class MileagePointEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mp_seq")
    private Long mp_seq;

    @Column(name = "mp_price")
    private Integer mp_price;

    @Column(name = "mp_expiration_date")
    private LocalDate mp_expiration_date;

    @JoinColumn(name = "mp_ui_seq")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
}
