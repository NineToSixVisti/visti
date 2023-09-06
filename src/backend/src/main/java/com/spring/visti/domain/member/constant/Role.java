package com.spring.visti.domain.member.constant;

public enum Role {

    USER, ADMIN, SUB;

    public static Role from(String role) {
        return Role.valueOf(role);
    }

}