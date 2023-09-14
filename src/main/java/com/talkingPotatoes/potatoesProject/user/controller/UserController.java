package com.talkingPotatoes.potatoesProject.user.controller;


import com.talkingPotatoes.potatoesProject.user.dto.*;
import com.talkingPotatoes.potatoesProject.user.dto.request.*;
import com.talkingPotatoes.potatoesProject.user.dto.request.LoginRequest;
import com.talkingPotatoes.potatoesProject.user.dto.request.UserIdRequest;
import com.talkingPotatoes.potatoesProject.user.dto.response.CheckUserResponse;
import com.talkingPotatoes.potatoesProject.user.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.talkingPotatoes.potatoesProject.common.dto.response.Response;
import com.talkingPotatoes.potatoesProject.user.mapper.UserDtoMapper;
import com.talkingPotatoes.potatoesProject.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final UserDtoMapper userDtoMapper;

    @PostMapping("/check-user")
    public ResponseEntity<Response> checkUser(@RequestBody @Valid UserIdRequest userIdRequest) {
        CheckUserDto checkUserDto = userService.checkUserId(userIdRequest.getUserId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("이메일을 검색했습니다.")
                        .data(userDtoMapper.toCheckUserResponse(checkUserDto))
                        .build());
    }

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
        emailService.sendEmailForSignUp(userIdRequest.getUserId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("이메일이 전송되었습니다.")
                        .build());
    }

    @GetMapping("/email-verify")
    public ResponseEntity<Response> emailVerify(@RequestParam("token") String token) {
        emailService.verifyForSignUp(token);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("이메일 인증이 완료되었습니다.")
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
    @GetMapping("/withdraw")
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

    /* UserSim 조회 */
    @GetMapping("/get-sim-user/{user-id}")
    public ResponseEntity<Response> getUserSim(@PathVariable("user-id") String userId) {
        List<SimUserDto> simUserDtoList = userService.selectSimUser(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("취향이 비슷한 유저에 대한 정보가 조회되었습니다.")
                        .data(simUserDtoList)
                        .build());
    }
}
