package org.team3.board.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.team3.board.entities.BoardData;
import org.team3.board.entities.QBoardData;

import java.util.List;

public interface BoardDataRepository extends JpaRepository<BoardData, Long>,
        QuerydslPredicateExecutor<BoardData> {

    @Query("SELECT DISTINCT b.board.bid FROM BoardData b WHERE b.member.userId=:userId")
    List<String> getUserBoards(@Param("userId") String userId);

    @Query("SELECT MIN(b.listOrder) FROM BoardData b WHERE b.parentSeq=:parentSeq")
    Long getLastReplyListOrder(@Param("parentSeq") Long seq);

    // 답글 유무 체크
    default boolean replyExists(Long seq) {
        QBoardData boardData = QBoardData.boardData;

        return exists(boardData.parentSeq.eq(seq));
    }
}