package org.team3.member.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

// 프로필에서 수정할 목록
@Data
@NoArgsConstructor
public class Profile {

    private String name;

    private String userId;

    private String password;

    private String nickName;

    private String email;

    public Profile(Member member) {
        this.name = member.getName();
        this.userId = member.getUserId();
        this.password = member.getPassword();
        this.nickName = member.getNickName();
        this.email = member.getEmail();
    }
}
