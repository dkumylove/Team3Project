package org.team3.member.entities;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Data
public class StockWithOptions {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    private Options options;
}
