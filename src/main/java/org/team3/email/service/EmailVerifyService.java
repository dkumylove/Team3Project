package org.team3.email.service;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.team3.commons.Utils;
import org.springframework.stereotype.Service;
import org.team3.commons.exceptions.AlertBackException;
import org.team3.member.repositories.MemberRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {

    private final EmailSendService sendService;
    private final HttpSession session;
    private final MemberRepository memberRepository;

    /**
     * 이메일 인증 번호 발급 전송
     *
     * @param email
     * @return
     */

    public boolean sendCode(String email, HttpServletRequest request) {
        return sendCodeAndCheck(email, null, request);

    }


        /*
        tplData = Objects.requireNonNullElse(tplData, new HashMap<>());

        String tplCode = tpl.equals("find_id") ? "userId" : "authNum";

        tplData.put(tplCode, authNum);
        }
        */





    /**
     * 발급 받은 인증번호와 사용자 입력 코드와 일치 여부 체크
     *
     * @param code
     * @return
     */
    public boolean check(int code) {

        Integer authNum = (Integer)session.getAttribute("EmailAuthNum");
        Long stime = (Long)session.getAttribute("EmailAuthStart");
        if (authNum != null && stime != null) {
            /* 인증 시간 만료 여부 체크 - 3분 유효시간 S */
            boolean isExpired = (System.currentTimeMillis() - stime.longValue()) > 1000 * 60 * 3;
            if (isExpired) { // 만료되었다면 세션 비우고 검증 실패 처리
                session.removeAttribute("EmailAuthNum");
                session.removeAttribute("EmailAuthStart");

                return false;
            }
            /* 인증 시간 만료 여부 체크 E */

            // 사용자 입력 코드와 발급 코드가 일치하는지 여부 체크
            boolean isVerified = code == authNum.intValue();
            // EmailAuthVerified 이메일 인증 여부
            session.setAttribute("EmailAuthVerified", isVerified);

            return isVerified;
        }
        return false;
    }





    /**
     * 이메일 인증 번호 발급 전송
     *
     * @param email
     * @param request
     * @return
     */
    public boolean sendCodeAndCheck(String email, String name, HttpServletRequest request) {
        int authNum = (int)(Math.random() * 99999);

        session.setAttribute("EmailAuthNum", authNum); // 난수
        session.setAttribute("EmailAuthStart", System.currentTimeMillis()); // 제한시간

        EmailMessage emailMessage = new EmailMessage();

        System.out.println(request.getRequestURI()); // api/verify/email

        Map<String, Object> tplData = new HashMap<>();
        if (request.getRequestURI().indexOf("/findid") != -1) {
                boolean checked = memberRepository.existsByEmailAndName(email, name);
                if(checked) {
                    emailMessage.setTo(email);
                    emailMessage.setSubject(Utils.getMessage("Email.findid.subject", "commons"));
                    emailMessage.setMessage(Utils.getMessage("Email.findid.message", "commons"));
                    tplData.put("authNum", authNum);
                    return sendService.sendMail(emailMessage, "findid", tplData);
                } else {
                    return false;
                }
        } else {
            emailMessage.setTo(email);
            emailMessage.setSubject(Utils.getMessage("Email.verification.subject", "commons"));
            emailMessage.setMessage(Utils.getMessage("Email.verification.message", "commons"));
            tplData.put("authNum", authNum);
            return sendService.sendMail(emailMessage, "auth", tplData);
        }
    }

    /**
     * 발급 받은 인증번호와 사용자 입력 코드와 일치 여부 체크
     *
     * @param code
     * @param email
     * @return
     */
    public boolean checkCode(int code, String email) {
        Integer authNum = (Integer) session.getAttribute("EmailAuthNum");
        Long stime = (Long) session.getAttribute("EmailAuthStart");
        if (authNum != null && stime != null) {
            /* 인증 시간 만료 여부 체크 - 3분 유효시간 S */
            boolean isExpired = (System.currentTimeMillis() - stime.longValue()) > 1000 * 60 * 3;
            if (isExpired) { // 만료되었다면 세션 비우고 검증 실패 처리
                session.removeAttribute("EmailAuthNum");
                session.removeAttribute("EmailAuthStart");
                return false;
            }
            /* 인증 시간 만료 여부 체크 E */

            // 사용자 입력 코드와 발급 코드가 일치하는지 여부 체크
            boolean isVerified = code == authNum.intValue();
            // EmailAuthVerified 이메일 인증 여부
            session.setAttribute("EmailAuthVerified", isVerified);
            return isVerified;
        }
        return false;
    }

    /**
     * 현재 세션에서 저장된 이메일 가져오기
     *
     * @return
     */
    public String getEmail() {
        return (String) session.getAttribute("Email");
    }

    /**
     * 현재 세션에서 저장된 회원명 가져오기
     *
     * @return
     */
    public String getName() {
        return (String) session.getAttribute("Name");
    }

}
