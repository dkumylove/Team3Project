package org.team3.board.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;


@Data
@Entity
@IdClass(BoardViewId.class)
public class BoardView {

    @Id
    private Long seq; // 게시글 번호
        
    /*
        1월 10일 
        이기흥 수정
     */
    @Id
    @Column(name="_uid") // uid는 예약어라 oracle에서 사용 못함. 
    private Integer uid; // viewUid
}
