package com.spring.visti.domain.storybox.constant;

public enum Position {

    HOST, GUEST;

    public static Position from(String role) {
        return Position.valueOf(role);
    }

}