package com.talkingPotatoes.potatoesProject.user.controller;

import java.io.IOException;
import java.util.List;

import com.talkingPotatoes.potatoesProject.user.dto.Auth;
import com.talkingPotatoes.potatoesProject.user.dto.TokenDto;
import com.talkingPotatoes.potatoesProject.user.dto.request.LoginRequest;
import com.talkingPotatoes.potatoesProject.user.dto.request.OAuthSignUpRequest;
import com.talkingPotatoes.potatoesProject.user.dto.request.UserIdRequest;
import com.talkingPotatoes.potatoesProject.user.dto.response.OAuthSignupResponse;
import com.talkingPotatoes.potatoesProject.user.dto.response.TokenResponse;
import com.talkingPotatoes.potatoesProject.user.entity.Role;
import com.talkingPotatoes.potatoesProject.user.service.EmailService;
import com.talkingPotatoes.potatoesProject.user.service.OAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.dto.UserGenreDto;
import com.talkingPotatoes.potatoesProject.user.dto.request.SignUpRequest;
import com.talkingPotatoes.potatoesProject.common.dto.response.Response;
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
    private final EmailService emailService;
    private final OAuthService oAuthService;
    private final UserDtoMapper userDtoMapper;
    private final UserGenreDtoMapper userGenreDtoMapper;

    @PostMapping("/signup")
    public ResponseEntity<Response> signUp(@RequestBody @Valid SignUpRequest signUpRequest) throws Exception {

        UserDto userDto = userDtoMapper.fromSignUpRequest(signUpRequest);
        userDto.setPlatform(Platform.NONE);
        userDto.setRole(Role.ACTIVE);

        UserDto resultDto = userService.signUp(userDto);

        emailService.sendSignUpMessage(resultDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("회원이 생성되었습니다.")
                        .build());
    }

    @PostMapping("/email-send")
    public ResponseEntity<Response> emailSend(@RequestBody UserIdRequest userIdRequest) throws Exception {
        emailService.sendEmail(userIdRequest.getUserId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("이메일이 전송되었습니다.")
                        .build());
    }

    @GetMapping("/email-verify")
    public ResponseEntity<Response> emailVerify(@RequestParam("token") String token) {
        emailService.verify(token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("이메일 인증이 완료되었습니다.")
                        .build());
    }

    // sns 로그인 이후
    @PostMapping("/signup/oauth")
    public ResponseEntity<Response> oAuthSignUp(@RequestBody OAuthSignUpRequest oAuthSignUpRequest) {
        UserDto userDto = userDtoMapper.fromOAuthSignUpRequest(oAuthSignUpRequest);
        List<UserGenreDto> userGenreDtoList = userGenreDtoMapper.fromSignupRequest(oAuthSignUpRequest.getGenreList());

        oAuthService.oAuthContinueSignUp(userDto, userGenreDtoList);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("회원이 생성되었습니다.")
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        TokenDto tokenDto = userService.login(userDtoMapper.fromLoginRequest(loginRequest));

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("로그인 되었습니다.")
                        .data(userDtoMapper.toTokenResponse(tokenDto))
                        .build());
    }
}
