package com.talkingPotatoes.potatoesProject.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.dto.UserGenreDto;
import com.talkingPotatoes.potatoesProject.user.dto.request.SignUpRequest;
import com.talkingPotatoes.potatoesProject.user.dto.response.Response;
import com.talkingPotatoes.potatoesProject.user.entity.Platform;
import com.talkingPotatoes.potatoesProject.user.mapper.UserDtoMapper;
import com.talkingPotatoes.potatoesProject.user.mapper.UserGenreDtoMapper;
import com.talkingPotatoes.potatoesProject.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;
	private final UserDtoMapper userDtoMapper;
	private final UserGenreDtoMapper userGenreDtoMapper;

	@PostMapping("/signup")
	public ResponseEntity<Response> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {

		UserDto userDto = userDtoMapper.fromSignUpRequest(signUpRequest);
		userDto.setPlatform(Platform.NONE);

		List<UserGenreDto> userGenreDtoList = userGenreDtoMapper.fromSignupRequest(signUpRequest.getGenreList());

		userService.signUp(userDto, userGenreDtoList);

		return ResponseEntity.status(HttpStatus.OK)
			.body(Response.builder()
				.message("회원이 생성되었습니다.")
				.build());
	}
}
