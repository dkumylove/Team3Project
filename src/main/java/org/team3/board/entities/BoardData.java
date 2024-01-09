package org.team3.board.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.team3.commons.entities.BaseMember;
import org.team3.member.entities.Member;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class BoardData extends BaseMember {

    @Id
    @GeneratedValue
    private Long seq;

    private String subject;
    private String content;

    @ManyToOne
    private Member member;

    /* 나(게시글)를 찜한 멤버 - 보류
    * 이다은 - 1월 9일
    * */
    @OneToMany(fetch = FetchType.LAZY)
    private List<Member> memberList=new ArrayList<>();

}
