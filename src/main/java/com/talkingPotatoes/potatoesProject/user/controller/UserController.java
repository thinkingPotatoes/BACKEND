package com.talkingPotatoes.potatoesProject.user.controller;


import com.talkingPotatoes.potatoesProject.user.dto.Auth;
import com.talkingPotatoes.potatoesProject.user.dto.TokenDto;
import com.talkingPotatoes.potatoesProject.user.dto.request.*;
import com.talkingPotatoes.potatoesProject.user.entity.Role;
import com.talkingPotatoes.potatoesProject.user.service.EmailService;
import com.talkingPotatoes.potatoesProject.user.service.OAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.common.dto.response.Response;
import com.talkingPotatoes.potatoesProject.user.entity.Platform;
import com.talkingPotatoes.potatoesProject.user.mapper.UserDtoMapper;
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

    @PostMapping("/signup")
    public ResponseEntity<Response> signUp(@RequestBody @Valid SignUpRequest signUpRequest) throws Exception {
        UserDto userDto = userDtoMapper.fromSignUpRequest(signUpRequest);

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

    /* sns 로그인 이후 */
    @PostMapping("/signup/oauth")
    // accessToken으로 user 값을 가져와야할지, userId를 requestBody에 넣어서 와야할지에 대한 고민
    public ResponseEntity<Response> oAuthSignUp(@RequestBody OAuthSignUpRequest oAuthSignUpRequest) {
        UserDto userDto = userDtoMapper.fromOAuthSignUpRequest(oAuthSignUpRequest);
        oAuthService.oAuthContinueSignUp(userDto);

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

    /* 회원탈퇴 */
    public ResponseEntity<Response> withdraw(@AuthenticationPrincipal Auth auth) {
        userService.withdraw(auth.getId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("회원이 탈퇴되었습니다.")
                        .build());
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Response> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        TokenDto tokenDto = userService.refreshToken(refreshTokenRequest.getRefreshToken());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("accessToken 재발급이 되었습니다.")
                        .data(userDtoMapper.toTokenResponse(tokenDto))
                        .build());
    }
}
