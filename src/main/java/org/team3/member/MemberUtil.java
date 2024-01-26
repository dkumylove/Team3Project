package org.team3.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.team3.member.entities.Authorities;
import org.team3.member.entities.Member;
import org.team3.member.service.MemberInfo;
import org.team3.member.service.MemberInfoService;


@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final HttpSession session;
    private final MemberInfoService memberInfoService;
    /**
     * 관리자 여부 확인
     * @return
     */
    public boolean isAdmin() {

        if(isLogin()) {
            return getMember().getAuthorities().stream().map(Authorities::getAuthority)
                    .anyMatch(a -> a == Authority.ADMIN || a == Authority.MANAGER);
        }
        return false;
    }

    /**
     * 로그인 여부 확인
     * @return
     */
    public boolean isLogin() {
        return getMember() != null;
    }

    /**
     * Member값 불러오기
     * @return
     */
    public Member getMember() {
        Member member = (Member) session.getAttribute("member");
        return member;
    }

    public static void clearLoginData(HttpSession session) {
        session.removeAttribute("username");
        session.removeAttribute("NotBlank_username");
        session.removeAttribute("NotBlank_password");
        session.removeAttribute("Global_error");
    }

    /**
     * 세션값 업데이트해줌
     */
    public void update() {
        if (!isLogin()) {
            return;
        }
        MemberInfo memberInfo = (MemberInfo)memberInfoService.loadUserByUsername(getMember().getUserId());
        Member member = memberInfo.getMember();
        session.setAttribute("member", member);
    }
}

