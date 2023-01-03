package com.greenart.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "option_info")
public class OptionInfoEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oi_seq")        private Long seq;
    @Column(name = "oi_option")     private Long option;
    @Column(name = "oi_price")      private Long price;
    @Column(name = "oi_pi_seq")     private Long piSeq;
    
}
