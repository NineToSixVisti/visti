package com.spring.visti.domain.storybox.constant;

public enum StoryType {
    LETTER, IMAGE, AUDIO, VIDEO;

    public static StoryType from(String type) {
        return StoryType.valueOf(type);
    }
}
