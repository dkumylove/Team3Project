package org.team3.board.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team3.board.entities.BoardData;

public interface BoardDataRepository extends JpaRepository<BoardData, Long> {

}