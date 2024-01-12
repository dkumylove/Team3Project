package org.team3.board.repositories;

import org.team3.board.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardRepository extends JpaRepository<Board, String>,
        QuerydslPredicateExecutor<Board> {

}
