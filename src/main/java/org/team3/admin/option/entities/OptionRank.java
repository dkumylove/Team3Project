package org.team3.admin.option.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(name="idx_rank_basic",
        columnList = "favorite ASC"))
public class OptionRank {

    private boolean active; // 사용여부
    private String optionDetail; // 게시판 설명
    
    @Id @Column(unique = true)
    private String optionname; // 옵션이름

    // private int rank;

    private int favorite; // 회원 수
}
