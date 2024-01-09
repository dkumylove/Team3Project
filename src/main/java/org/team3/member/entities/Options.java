package org.team3.member.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Options {
    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StockWithOptions> stockWithOptions=new ArrayList<>();
}
