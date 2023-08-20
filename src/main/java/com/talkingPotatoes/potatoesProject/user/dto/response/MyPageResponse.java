package com.talkingPotatoes.potatoesProject.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyPageResponse {
    private String userId;

    private String nickname;
}
