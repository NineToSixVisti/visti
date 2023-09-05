package com.spring.demo.domain.member.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MemberType {

    KAKAO,
    NAVER,
    SOCIAL
    ;

    public static MemberType from(String type) {
        return MemberType.valueOf(type.toUpperCase());
    }

    public static boolean isMemberType(String type) {
        List<MemberType> memberTypes = Arrays.stream(MemberType.values())
                .filter(memberType -> memberType.name().equals(type))
                .toList();
        return memberTypes.size() != 0;
    }

}
