package com.talkingPotatoes.potatoesProject.user.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class TokenResponse extends Response {
    private String accessToken;

    private String refreshToken;
}
