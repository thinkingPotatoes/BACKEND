package com.talkingPotatoes.potatoesProject.user.mapper;

import com.talkingPotatoes.potatoesProject.user.dto.*;
import com.talkingPotatoes.potatoesProject.user.dto.request.LoginRequest;
import com.talkingPotatoes.potatoesProject.user.dto.request.MyPageRequest;
import com.talkingPotatoes.potatoesProject.user.dto.response.*;
import org.mapstruct.Mapper;

import com.talkingPotatoes.potatoesProject.user.dto.request.SignUpRequest;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
	UserDto fromSignUpRequest(SignUpRequest signUpRequest);

    UserDto fromLoginRequest(LoginRequest loginRequest);

    TokenResponse toTokenResponse(TokenDto tokenDto);

	MyPageResponse toMyPageResponse(MyPageDto myPageDto);

    MyPageDto fromMyPageRequest(MyPageRequest myPageRequest);

    BlogInfoResponse toBlogInfoResponse(BlogInfoDto blogInfoDto);

    MyInfoResponse toMyInfoResponse(UserDto userDto);

    CheckUserResponse toCheckUserResponse(CheckUserDto checkUserDto);
}
