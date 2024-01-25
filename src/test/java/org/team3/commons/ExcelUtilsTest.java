package org.team3.commons;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ExcelUtilsTest {
    @Autowired
    private ExcelUtils utils;

    @Test
    @DisplayName("엑셀 파일 -> List<String[]> 으로 변환 테스트")
    void test1() {
        List<String[]> data = utils.getData("data/option.xlsx", new int[] {0, 1}, 0);
        data.forEach(s -> System.out.println(Arrays.toString(s)));
    }

    @Test
    @DisplayName("엑셀파일 -> List<String[]> -> SQL 목록 변환 테스트")
    void test2() {
        String[] fields = { "optionname", "category" };
        List<String> sqlData = utils.makeSql("data/option.xlsx", new int[] {0, 1}, 0, "OPTIONS", fields).toList();
        sqlData.forEach(System.out::println);
    }

    @Test
    @DisplayName("엑셀파일 -> List<String[]> -> SQL 파일 변환 테스트")
    void test3() {
        String destPath = "data/option.sql";
        String[] fields = { "optionname", "category" };
        utils.makeSql("data/option.xlsx", new int[] {0, 1}, 0, "OPTIONS", fields).toFile(destPath);
        File file = new File(destPath);

        assert(file.exists());
    }

    @Test
    @DisplayName("엑셀파일 -> delimiter 문자열을 결합한 List<String> 변환 테스트")
    void test4() {
        List<String> data = utils.getData("data/option.xlsx", new int[] {0, 1}, 0,"_");
        data.forEach(System.out::println);
    }
}
