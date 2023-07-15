package com.talkingPotatoes.potatoesProject.user.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
public class OAuthSignupResponse extends Response {
    private UUID id;
}
