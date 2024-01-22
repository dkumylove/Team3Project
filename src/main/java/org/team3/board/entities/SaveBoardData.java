package org.team3.board.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;
import org.team3.commons.entities.Base;

@Data
@Entity
@IdClass(SaveBoardDataId.class)
public class SaveBoardData extends Base {

    @Id
    private Long bSeq; // 게시글 번호

    @Id
    private Long mSeq; // 회원번호
}