package com.talkingPotatoes.potatoesProject.user.mapper;

import com.talkingPotatoes.potatoesProject.user.dto.response.MyPageResponse;
import org.mapstruct.Mapper;

import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.dto.request.SignUpRequest;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
	UserDto fromSignUpRequest(SignUpRequest signUpRequest);

	MyPageResponse toMyPageResponse(UserDto userDto);
}
