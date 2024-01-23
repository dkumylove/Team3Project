package org.team3.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.team3.board.repositories.BoardDataRepository;

@SpringBootTest
public class ReplyTest {
    @Autowired
    private BoardDataRepository boardDataRepository;

    @Test
    void test1() {
        Long listOrder = boardDataRepository.getLastReplyListOrder(2L);
        System.out.println(listOrder);

        Long listOrder2 = boardDataRepository.getLastReplyListOrder(1L);
        System.out.println(listOrder2);
    }
}
