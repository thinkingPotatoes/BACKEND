package com.talkingPotatoes.potatoesProject.user.service;

import com.talkingPotatoes.potatoesProject.user.dto.UserDto;

public interface UserService {
	UserDto signUp(UserDto memberDto);
}
