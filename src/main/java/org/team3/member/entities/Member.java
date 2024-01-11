package org.team3.member.entities;

import jakarta.persistence.*;
import lombok.*;
import org.team3.board.entities.BoardData;
import org.team3.commons.entities.Base;
import org.team3.file.entities.FileInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ToString.Exclude  // 순환참조 방지
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Authorities> authorities = new ArrayList<>();

    @Transient  // 내부사용목적
    private FileInfo profileImage;   // path, url


    /* 팔로워 */
    @OneToMany(mappedBy = "fromMember", fetch = FetchType.LAZY)
    private List<Follow> followers = new ArrayList<>();

    /* 팔로잉 */
    @OneToMany(mappedBy = "toMember", fetch = FetchType.LAZY)
    private List<Follow> followings = new ArrayList<>();

    /* 내가 찜한 게시글 - 보류
    * 이다은 - 1월 9일
    * */
    /* 수정 중... map으로 해야되는지 favorite 엔티티를 만들어야 되는지..
    @OneToMany(mappedBy = "member")
    private Map<Boolean, BoardData> favoriteBoardDataList = new HashMap<>();
     */
}
