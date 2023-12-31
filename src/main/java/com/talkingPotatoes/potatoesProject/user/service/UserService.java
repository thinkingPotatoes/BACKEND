package com.talkingPotatoes.potatoesProject.user.service;

import com.talkingPotatoes.potatoesProject.user.dto.CheckUserDto;
import com.talkingPotatoes.potatoesProject.user.dto.SimUserDto;
import com.talkingPotatoes.potatoesProject.user.dto.TokenDto;
import com.talkingPotatoes.potatoesProject.user.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
	UserDto signUp(UserDto memberDto);

    TokenDto login(UserDto userDto);

    void withdraw(UUID id);

    TokenDto refreshToken(String refreshToken);

    CheckUserDto checkUserId(String userId);

    List<SimUserDto> selectSimUser(String userId);

    TokenDto initMovieToken(String userId);
}
