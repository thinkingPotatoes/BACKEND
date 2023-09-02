package com.talkingPotatoes.potatoesProject.user.dto.response;

import com.talkingPotatoes.potatoesProject.user.entity.Platform;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyInfoResponse {
    private String userId;

    private String nickname;

    private Platform platform;
}
