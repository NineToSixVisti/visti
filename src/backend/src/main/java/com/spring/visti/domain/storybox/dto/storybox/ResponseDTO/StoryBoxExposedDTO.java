package com.spring.visti.domain.storybox.dto.storybox.ResponseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.visti.domain.storybox.entity.StoryBox;
import com.spring.visti.utils.urlutils.SecurePathUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoryBoxExposedDTO {

    private String encryptedId;

    private String boxImgPath;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "asia/seoul")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "asia/seoul")
    private LocalDateTime finishedAt;
    private Boolean blind;

    @Builder
    public StoryBoxExposedDTO(String encryptedId,
                              String boxImgPath, String name,
                              LocalDateTime createdAt, LocalDateTime finishedAt,
                              Boolean blind){
        this.encryptedId = encryptedId;
        this.boxImgPath = boxImgPath;
        this.name = name;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
        this.blind = blind;
    }

    public static StoryBoxExposedDTO of(StoryBox storyBox){
        String encryptedId = SecurePathUtil.encryptAndEncode(String.valueOf(storyBox.getId()));
//        String isDecrypted = SecurePathUtil.decodeAndDecrypt(encryptedId);
//        System.out.println("이거를 확인해주세요" + isDecrypted);
        return StoryBoxExposedDTO.builder()
                .encryptedId(encryptedId)
                .boxImgPath(storyBox.getBoxImgPath())
                .name(storyBox.getName())
                .createdAt(storyBox.getCreatedAt())
                .finishedAt(storyBox.getFinishedAt())
                .blind(storyBox.getBlind())
                .build();
    }
}
