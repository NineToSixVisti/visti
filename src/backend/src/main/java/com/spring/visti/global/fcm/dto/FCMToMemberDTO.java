package com.spring.visti.global.fcm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FCMToMemberDTO {

    private String fireBaseToken;

    @Builder
    public FCMToMemberDTO(String fireBaseToken){ this.fireBaseToken = fireBaseToken; }

}
