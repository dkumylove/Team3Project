package org.team3.mypage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.team3.member.controllers.MypageController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class mypageUpdateControllerTest {

    @Autowired
    MockMvc mockMvc;

    @DisplayName(("프로필 수정하기 - 입력값 정상"))
    @Test
    void updateProfile() throws Exception {

    }
}
