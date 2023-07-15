package com.talkingPotatoes.potatoesProject.user.service;

import com.talkingPotatoes.potatoesProject.user.dto.Auth;
import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.dto.UserGenreDto;
import com.talkingPotatoes.potatoesProject.user.entity.Platform;

import java.io.IOException;
import java.util.List;

public interface OAuthService {
    void request(Platform socialLoginPath) throws IOException;

    Auth oAuthLogin(Platform socialLoginPath, String code);

    void oAuthContinueSignUp(UserDto userDto, List<UserGenreDto> userGenreDtoList);
}
