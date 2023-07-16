package com.talkingPotatoes.potatoesProject.user.mapper;

import com.talkingPotatoes.potatoesProject.user.dto.request.LoginRequest;
import com.talkingPotatoes.potatoesProject.user.dto.request.OAuthSignUpRequest;
import org.mapstruct.Mapper;

import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.dto.request.SignUpRequest;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
	UserDto fromSignUpRequest(SignUpRequest signUpRequest);

    UserDto fromOAuthSignUpRequest(OAuthSignUpRequest oAuthSignUpRequest);

    UserDto fromLoginRequest(LoginRequest loginRequest);
}
