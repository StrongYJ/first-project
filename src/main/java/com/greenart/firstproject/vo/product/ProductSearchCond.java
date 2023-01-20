package com.greenart.firstproject.vo.product;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchCond {
    private String type;
    private String level;
    private Integer sweetness;
    private Integer sour;
    private Integer soda;
    private String raw;
    private List<Integer> price;
}
