package com.talkingPotatoes.potatoesProject.user.service;

import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.dto.UserGenreDto;

import java.util.List;

public interface OAuthService {
    void oAuthContinueSignUp(UserDto userDto);
}
