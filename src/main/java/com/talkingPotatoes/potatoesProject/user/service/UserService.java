package com.talkingPotatoes.potatoesProject.user.service;

import java.util.List;

import com.talkingPotatoes.potatoesProject.user.dto.TokenDto;
import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.dto.UserGenreDto;

public interface UserService {
	UserDto signUp(UserDto memberDto,
                   List<UserGenreDto> userGenreDtoList);

    TokenDto login(UserDto userDto);
}
