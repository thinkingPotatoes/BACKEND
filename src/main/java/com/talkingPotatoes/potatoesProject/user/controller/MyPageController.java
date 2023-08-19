package com.talkingPotatoes.potatoesProject.user.controller;

import com.talkingPotatoes.potatoesProject.common.dto.response.Response;
import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.dto.request.NicknameRequest;
import com.talkingPotatoes.potatoesProject.user.mapper.UserDtoMapper;
import com.talkingPotatoes.potatoesProject.user.service.MyPageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/my-page")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;
    private final UserDtoMapper userDtoMapper;

    /* 마이페이지 정보 조회 */
    @GetMapping
    public ResponseEntity<Response> getMyPage(@RequestHeader(value = "userId") UUID loginId) {

        UserDto userDto = myPageService.get(loginId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("회원정보가 조회되었습니다.")
                        .data(userDtoMapper.toMyPageResponse(userDto))
                        .build());
    }

    /* 비밀번호 수정 */


    /* 닉네임 수정 */
    @PatchMapping("/nickname")
    public ResponseEntity<Response> updateNickname(@RequestHeader(value = "userId") UUID loginId,
                                                   @RequestBody @Valid  NicknameRequest nicknameRequest) {
        UserDto userDto = myPageService.updateNickname(loginId, nicknameRequest.getNickname());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("닉네임이 수정되었습니다.")
                        .data(userDtoMapper.toMyPageResponse(userDto))
                        .build());
    }
}
