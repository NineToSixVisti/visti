package com.spring.visti.domain.member.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.spring.visti.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + member.getRole()));
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // lastPasswordChangeDate는 Member 엔터티에서 패스워드가 마지막으로 변경된 날짜를 나타내는 필드입니다.
        LocalDateTime lastPasswordChangeDate = member.getUpdatedAt();

        // 만약 lastPasswordChangeDate가 90일보다 오래되었다면 false를 반환하여 자격 증명이 만료되었음을 알립니다.
        return !lastPasswordChangeDate.isBefore(LocalDateTime.now().minusDays(90));
    }

    @Override
    public boolean isEnabled() {
        return member.getStatus();
    }
}
