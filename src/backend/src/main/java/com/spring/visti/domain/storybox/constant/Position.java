package com.spring.visti.domain.storybox.constant;

public enum Position {

    HOST, GUEST;

    public static Position from(String type) {
        return Position.valueOf(type);
    }

}