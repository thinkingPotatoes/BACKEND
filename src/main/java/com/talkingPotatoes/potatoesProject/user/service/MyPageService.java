package com.talkingPotatoes.potatoesProject.user.service;

import com.talkingPotatoes.potatoesProject.user.dto.UserDto;

import java.util.UUID;

public interface MyPageService {
    UserDto get(UUID loginId);

    UserDto updateNickname(UUID loginId, String nickname);
}
