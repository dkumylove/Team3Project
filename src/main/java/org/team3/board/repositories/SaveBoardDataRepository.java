package org.team3.board.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.team3.board.entities.QSaveBoardData;
import org.team3.board.entities.SaveBoardData;
import org.team3.board.entities.SaveBoardDataId;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

public interface SaveBoardDataRepository extends JpaRepository<SaveBoardData, SaveBoardDataId>, QuerydslPredicateExecutor<SaveBoardData> {

    /**
     * 회원번호별 게시글 번호 목록
     *
     * @param mSeq
     * @return
     */
    default List<Long> getBoardDataSeqs(Long mSeq) {
        QSaveBoardData saveBoardData = QSaveBoardData.saveBoardData;

        List<SaveBoardData> items = (List<SaveBoardData>)findAll(saveBoardData.mSeq.eq(mSeq), Sort.by(desc("createdAt")));

        return items.stream().map(SaveBoardData::getBSeq).toList();
    }
}