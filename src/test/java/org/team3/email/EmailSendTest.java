package org.team3.email;

import jakarta.servlet.http.HttpServletRequest;
import org.team3.email.service.EmailMessage;
import org.team3.email.service.EmailSendService;
import org.team3.email.service.EmailVerifyService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class EmailSendTest {

    @Autowired
    private EmailSendService emailSendService;
    @Autowired
    private EmailVerifyService emailVerifyService;
    @Autowired
    private HttpServletRequest request;

    @Test
    public void emailSendTest1(){
        EmailMessage message = new EmailMessage("eun081217@gmail.com", "제목...", "내용...");
        boolean success = emailSendService.sendMail(message);
        // assertTrue(success);
    }

    @Test
    public void sendWithTplTest(){
        EmailMessage message = new EmailMessage("bin0696@naver.com", "어우어려워,,,", "어렵다..,,");
        Map<String, Object> tplData = new HashMap<>();
        tplData.put("authNum", "123456");
        boolean success = emailSendService.sendMail(message, "auth", tplData);
    }

    @Test
    @DisplayName("이메일 인증 번호 전송 테스트")
    void emailVerifyTest() {
        boolean result = emailVerifyService.sendCode("bin0696@naver.com", request, "auth", null);
        assertTrue(result);
    }

    @Test
    @DisplayName("아이디 찾기 함수 테스트")
    void findUserIdTest() {
        Map<String, Object> tplData = new HashMap<>();
        tplData.put("userId", "user01");
       boolean result =  emailVerifyService.sendCode("bin0696@naver.com", request, "find_id", tplData);
        assertTrue(result);
    }
}
