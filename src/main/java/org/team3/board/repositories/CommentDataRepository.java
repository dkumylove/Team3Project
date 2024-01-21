package org.team3.board.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.team3.board.entities.CommentData;
import org.team3.board.entities.QCommentData;

public interface CommentDataRepository extends JpaRepository<CommentData, Long>,
    QuerydslPredicateExecutor<CommentData> {

    /**
     * 게시글별 댓글 갯수
     *
     * @param boardDataSeq
     * @return
     */
    default int getTotal(Long boardDataSeq) {
        QCommentData commentData = QCommentData.commentData;

        return (int)count(commentData.boardData.seq.eq(boardDataSeq));
    }
}
