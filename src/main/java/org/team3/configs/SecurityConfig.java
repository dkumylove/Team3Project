package org.team3.configs;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.team3.member.service.LoginFailurdHandler;
import org.team3.member.service.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.team3.member.service.MemberInfoService;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig  {

    private final MemberInfoService memberInfoService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 설정 무력화
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /* 인증설정 S - 로그인, 로그아웃 S */
        http.formLogin(f -> {
            f.loginPage("/member/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccessHandler())
                    .failureHandler(new LoginFailurdHandler());
        });

        http.logout(c -> {
            c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                    .logoutSuccessUrl("/"); // 로그아웃 시 메인 경로로 이동하도록 수정 - 이다은 1/11
        });
        /* 인증설정 E - 로그인, 로그아웃 E */

        /**
         * 마이페이지 작업으로 잠시 주석처리함
         * 이지은 1월 10일
         */
//        /* 인가설정 S - 접근 통제 */
//        http.authorizeHttpRequests(c -> {
//            c.requestMatchers("/mypage/**").authenticated() // 회원 전용
//                    //.requestMatchers("/admin/**").hasAnyAuthority("ADMIN", "MANAGER")
//                    .anyRequest().permitAll(); // 그외 모든 페이지는 모두 접근 가능
//        });
//
//
//
//        http.exceptionHandling(c -> {
//
//            c.authenticationEntryPoint(( req,  res,  e) -> {
//                String URL = req.getRequestURI();
//                if(URL.indexOf("/admin") != -1) {  // 관리자 페이지
//                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED);  //401
//                } else {  // 회원 전용 페이지
//                    res.sendRedirect(req.getContextPath()+"/member/login");
//                }
//            });
//        });
        /* 인가설정 E - 접근 통제 */

        /**
         * 같은 출처의 사이트에서는 ifrm 사용할수 있게 허용
         */
        http.headers(c -> c.frameOptions(f -> f.sameOrigin()));

        /* 자동 로그인 설정 S */
        http.rememberMe(c -> {
            c.rememberMeParameter("autoLogin") // 자동 로그인으로 사용할 요청 파리미터 명, 기본값은 remember-me
                    .tokenValiditySeconds(60 * 60 * 24 * 30) // 로그인을 유지할 기간(30일로 설정), 기본값은 14일
                    .userDetailsService(memberInfoService) // 재로그인을 하기 위해서 인증을 위한 필요 UserDetailsService 구현 객체
                    .authenticationSuccessHandler(new LoginSuccessHandler()); // 자동 로그인 성공시 처리 Handler

        });
        /* 자동 로그인 설정 E */

        return http.build();
    }

    /**
     * 비밀번호 해시화
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
