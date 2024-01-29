package org.team3.board.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.team3.board.entities.BoardView;
import org.team3.board.entities.BoardViewId;

public interface BoardViewRepository extends JpaRepository<BoardView, BoardViewId>,
        QuerydslPredicateExecutor<BoardView> {
}
