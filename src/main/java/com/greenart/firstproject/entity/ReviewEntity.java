package com.greenart.firstproject.entity;

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

    @Column(name = "ri_ui_seq") 
    private Long uiSeq;

    @Column(name = "ri_oi_seq") 
    private Long oiSeq;
    
}
