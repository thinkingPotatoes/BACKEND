package com.talkingPotatoes.potatoesProject.user.mapper;

import com.talkingPotatoes.potatoesProject.user.dto.BlogInfoDto;
import com.talkingPotatoes.potatoesProject.user.dto.MyPageDto;
import com.talkingPotatoes.potatoesProject.user.dto.TokenDto;
import com.talkingPotatoes.potatoesProject.user.dto.request.LoginRequest;
import com.talkingPotatoes.potatoesProject.user.dto.request.MyPageRequest;
import com.talkingPotatoes.potatoesProject.user.dto.request.OAuthSignUpRequest;
import com.talkingPotatoes.potatoesProject.user.dto.response.BlogInfoResponse;
import com.talkingPotatoes.potatoesProject.user.dto.response.MyInfoResponse;
import com.talkingPotatoes.potatoesProject.user.dto.response.TokenResponse;
import com.talkingPotatoes.potatoesProject.user.dto.response.MyPageResponse;
import org.mapstruct.Mapper;

import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.dto.request.SignUpRequest;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
	UserDto fromSignUpRequest(SignUpRequest signUpRequest);

    UserDto fromOAuthSignUpRequest(OAuthSignUpRequest oAuthSignUpRequest);

    UserDto fromLoginRequest(LoginRequest loginRequest);

    TokenResponse toTokenResponse(TokenDto tokenDto);

	MyPageResponse toMyPageResponse(MyPageDto myPageDto);

    MyPageDto fromMyPageRequest(MyPageRequest myPageRequest);

    BlogInfoResponse toBlogInfoResponse(BlogInfoDto blogInfoDto);

    MyInfoResponse toMyInfoResponse(UserDto userDto);
}
