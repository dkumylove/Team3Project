package org.team3.board;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.team3.commons.Utils;

@SpringBootTest
public class UtilsTest {
    @Autowired
    private Utils utils;

    @Test
    void test1() {
        String data = utils.confirmDelete();
        System.out.println(data);
    }
}
