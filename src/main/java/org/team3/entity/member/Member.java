//package org.team3.entity.member;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.AssertTrue;
//import lombok.Data;
//import lombok.ToString;
//import org.team3.commons.entities.Base;
//import org.team3.entity.IndicatorsType;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//@Entity
//public class Member extends Base {
//
//    @Id
//    @GeneratedValue
//    private Long seq;  // 회원번호
//
//    @Column(length = 80, nullable = false, unique = true)
//    private String email;  // 이메일
//
////    @Column(length = 40, nullable = false)
////    private boolean emailAuthentication;  // 이메일 인증
//
//    @Column(length = 40, nullable = false, unique = true)
//    private String userId;  // 아이디
//
//    @Column(length = 65, nullable = false)
//    private String password;  // 비번
//
////    @Column(length = 65, nullable = false)
////    private String confirmPw;  // 버번확인
//
//    @Column(length = 40, nullable = false)
//    private String name;  // 회원명 닉네임
//    /*
//    @ToString.Exclude  // 순환참조 방지
//    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
//    private List<Authorities> authorities = new ArrayList<>();
//
////    @Enumerated(EnumType.STRING)
////    @Column(length=15, nullable = false)
////    private MemberType mtype = MemberType.USER;  // 회원타입
//
//    @Transient  // 내부사용목적
//    private FileInfo profileImage;   // path, url
//
//    @Enumerated(EnumType.STRING)
//    @Column(length=40, nullable = false)
//    private IndicatorsType sIndicator = IndicatorsType.VOLUMEINDICATOR;  // 보조지표
//
//    @AssertTrue   // 해당 필드의 값이 true인지를 검증
//    private boolean agree;  // 약관동의
//
//    팔로우 팔로워 다은껄로
//    */
//}