package org.team3.member.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.team3.admin.option.entities.Options;
import org.team3.board.entities.BoardData;
import org.team3.commons.entities.Base;
import org.team3.file.entities.FileInfo;
import org.team3.member.Authority;

import java.util.*;

/* 테스트를 위해 추가 S - 이다은 1/11 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
/* 테스트를 위해 추가 E - 이다은 1/11 */
@Data
@Entity
public class Member extends Base {

    @Id @GeneratedValue
    private Long seq;  // 회원번호

    @Column(length=65, nullable = false)
    private String gid;  // 그룹아이디

    @Column(length=80, nullable = false, unique = true)
    private String email;

    @Column(length=40, nullable = false, unique = true)
    private String userId;

    @Column(length=65, nullable = false)
    private String password;

    @Column(length=40, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String nickName; // 닉네임

    private boolean act = true; // 활동 여부 : 이다은(1월 24일) true로 디폴트값 설정

    @ToString.Exclude  // 순환참조 방지
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Authorities> authorities = new ArrayList<>();

    @Transient  // 내부사용목적
    private FileInfo profileImage;   // path, url

    // 권한 추가
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Options> option = new ArrayList<>();

//    @OneToMany(fetch = FetchType.LAZY)
//    @Column(name = "option_name")
//    private List<Options> optionsList = new ArrayList<>();


    // private String[] option;

// 주석처리 해놓을게요 - 1월 18일 이다은
//    /* 팔로우때문에 추가한 엔티티 1월16일 이지은 */
//    // 다대다 관계로 팔로우 관계 설정
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "follow",
//            joinColumns = @JoinColumn(name = "follower_id"),
//            inverseJoinColumns = @JoinColumn(name = "following_id"))
//    private Set<Member> followers = new HashSet<>();
//
//    @ManyToMany(mappedBy = "followers", fetch = FetchType.LAZY)
//    private Set<Member> followings = new HashSet<>();
//    /* 팔로우때문에 추가한 엔티티 */
//

//
//    /* 팔로워 */
//    @OneToMany(mappedBy = "fromMember", fetch = FetchType.LAZY)
//    private List<Follow> followers = new ArrayList<>();
//
//    /* 팔로잉 */
//    @OneToMany(mappedBy = "toMember", fetch = FetchType.LAZY)
//    private List<Follow> followings = new ArrayList<>();


    /* 내가 찜한 게시글 - 보류
    * 이다은 - 1월 9일
    * */
    /* 수정 중... map으로 해야되는지 favorite 엔티티를 만들어야 되는지..
    @OneToMany(mappedBy = "member")
    private Map<Boolean, BoardData> favoriteBoardDataList = new HashMap<>();
     */

    /*
    김현교 2024/1/12
    임시적으로 넣어둔 엔티티
     */
    private String updateMemberMail;
    private String updateMemberNickname;
    private String updateMemberPassword;
    private String deleteMember;

    // 김현교 1/19
    // 사용자 활성화 상태
    private boolean enabled = true;

}
