package com.spring.visti.domain.storybox.dto.storybox.RequestDTO;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.entity.StoryBox;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryBoxBuildDTO {

//    private String boxImgPath;
    private String name;
    private String detail;
    private Boolean blind;
    private LocalDateTime finishedAt;

    @Builder
    public StoryBoxBuildDTO(String name, String detail, Boolean blind, LocalDateTime finishedAt){
//        this.boxImgPath = boxImgPath;
        this.name = name;
        this.detail = detail;
        this.blind = blind;
        this.finishedAt = finishedAt;

    }

    public StoryBox toEntity(Member member, String boxImgPath){

        return  StoryBox.builder()
                .creator(member)
                .boxImgPath(boxImgPath)
                .name(name)
                .detail(detail)
                .blind(blind)
                .finishedAt(finishedAt)
                .build();
    }
}
