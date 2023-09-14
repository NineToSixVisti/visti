package com.spring.visti.common.items;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.dto.storybox.RequestDTO.StoryBoxBuildDTO;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class StoryBox {

    public final static String boxImgPath = "";
    public final static String name = "테스트 스토리박스";
    public final static String detail = "이 편지는 20년전 런던에서 부터 시작되었으며....";

    public final static Boolean blind = true;
    public static LocalDateTime finishedAt;

    public static String token;
    public static LocalDateTime expireTime;


    public static StoryBoxBuildDTO 스토리박스_생성(){
        LocalDateTime now = LocalDateTime.now();
        StoryBox.finishedAt = now.plusHours(2);
        return StoryBoxBuildDTO.builder()
                .boxImgPath(boxImgPath)
                .name(name)
                .detail(detail)
                .blind(blind)
                .finishedAt(StoryBox.finishedAt)
                .build();
    }
}
