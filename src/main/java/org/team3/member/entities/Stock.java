package org.team3.member.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * 주식 엔티티 - 미완성(보류)
 * 보조지표와 주식은 M:M관계라서 StockWithOptions로 매핑 해둠...
 * 이다은 - 1월 9일
 */
//@Entity
//@Data
//public class Stock {
//    @Id
//    @GeneratedValue
//    private Long id;
//
//    private String name;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<StockWithOptions> stockWithOptions;
//}
