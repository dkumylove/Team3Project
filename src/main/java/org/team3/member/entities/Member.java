package org.team3.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.team3.board.entities.BoardData;
import org.team3.commons.entities.Base;
import org.team3.file.entities.FileInfo;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Member extends Base {
    @Id @GeneratedValue
    private Long seq;

    @Column(length=65, nullable = false)
    private String gid;

    @Column(length=80, nullable = false, unique = true)
    private String email;

    @Column(length=40, nullable = false, unique = true)
    private String userId;

    @Column(length=65, nullable = false)
    private String password;

    @Column(length=40, nullable = false)
    private String name;

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
    @OneToMany(mappedBy = "member")
    private List<BoardData> favoriteBoardDataList = new ArrayList<>();
}
