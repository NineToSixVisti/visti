package com.spring.visti.domain.storybox.dto.storybox.RequestDTO;

import com.spring.visti.domain.member.entity.Member;
import com.spring.visti.domain.storybox.entity.StoryBox;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class StoryBoxBuildDTO {

//    private String boxImgPath;
    private String name;
    private String detail;
    private Boolean blind;
    private LocalDate finishedAt;

    @Builder
    public StoryBoxBuildDTO(String name, String detail, Boolean blind, LocalDate finishedAt){
//        this.boxImgPath = boxImgPath;
        this.name = name;
        this.detail = detail;
        this.blind = blind;
        this.finishedAt = finishedAt;

    }

    public StoryBox toEntity(Member member, String boxImgPath){
        String dateTimeString = finishedAt + "T00:00:00"; // 또는 다른 원하는 시간

        // "yyyy-MM-ddTHH:mm:ss" 형식의 문자열을 LocalDateTime으로 파싱
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
        
        return  StoryBox.builder()
                .creator(member)
                .boxImgPath(boxImgPath)
                .name(name)
                .detail(detail)
                .blind(blind)
                .finishedAt(localDateTime)
                .build();
    }
}
