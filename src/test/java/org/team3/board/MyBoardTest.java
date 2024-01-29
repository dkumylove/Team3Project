package org.team3.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.team3.board.repositories.BoardDataRepository;

import java.util.List;

@SpringBootTest
public class MyBoardTest {
    @Autowired
    private BoardDataRepository repository;

    @Test
    void test1() {
        List<String> bids = repository.getUserBoards("user01");
        System.out.println(bids);
    }
}
