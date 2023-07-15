package com.talkingPotatoes.potatoesProject.user.dto;

import lombok.Getter;

import java.util.UUID;

// jwt에 들어갈 정보
@Getter
public class Auth {
    private UUID id;

    private String userId;

}
