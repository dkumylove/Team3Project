package org.team3.member.service;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.team3.member.entities.Member;

import java.util.Collection;

@Data
@Builder
public class MemberInfo implements UserDetails {

    private String email;
    private String userId;
    private String password;
    private Member member;
    private boolean enable;
    private boolean lock;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !lock;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 회원탈되하는 부분
     * @return
     */
    @Override
    public boolean isEnabled() {
        return enable;
    }
}
