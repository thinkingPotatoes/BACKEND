package com.talkingPotatoes.potatoesProject.user.controller;

import com.talkingPotatoes.potatoesProject.user.dto.BlogInfoDto;
import com.talkingPotatoes.potatoesProject.user.dto.MyPageDto;
import com.talkingPotatoes.potatoesProject.user.dto.request.MyPageRequest;
import com.talkingPotatoes.potatoesProject.user.dto.request.PasswordRequest;
import com.talkingPotatoes.potatoesProject.common.dto.response.Response;
import com.talkingPotatoes.potatoesProject.user.dto.Auth;
import com.talkingPotatoes.potatoesProject.user.dto.UserDto;
import com.talkingPotatoes.potatoesProject.user.mapper.UserDtoMapper;
import com.talkingPotatoes.potatoesProject.user.service.MyPageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/my-page")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;
    private final UserDtoMapper userDtoMapper;

    /* 마이페이지 정보 조회 */
    @GetMapping
    public ResponseEntity<Response> getMyPage(@AuthenticationPrincipal Auth auth) {

        MyPageDto myPageDto = myPageService.getMyPage(auth.getId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("회원정보가 조회되었습니다.")
                        .data(userDtoMapper.toMyPageResponse(myPageDto))
                        .build());
    }

    /* 내 정보 조회 */
    @GetMapping("/user")
    public ResponseEntity<Response> getMyInfo(@AuthenticationPrincipal Auth auth) {

        UserDto userDto = myPageService.getUserInfo(auth.getId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("회원정보가 조회되었습니다.")
                        .data(userDtoMapper.toMyInfoResponse(userDto))
                        .build());
    }

    /* 필로그 정보 조회 */
    @GetMapping("/filog")
    public ResponseEntity<Response> getBlogInfo(@AuthenticationPrincipal Auth auth) {
        BlogInfoDto blogInfoDto = myPageService.getBlogInfo(auth.getId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("필로그 정보가 조회되었습니다.")
                        .data(userDtoMapper.toBlogInfoResponse(blogInfoDto))
                        .build());
    }

    /* 마이페이지 정보 전부 수정 */
    @PutMapping
    public ResponseEntity<Response> update(@AuthenticationPrincipal Auth auth,
                                           @RequestBody @Valid MyPageRequest myPageRequest) {
        myPageService.update(auth.getId(), userDtoMapper.fromMyPageRequest(myPageRequest));

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("회원정보가 수정되었습니다.")
                        .build());
    }

    /* 비밀번호 수정 */
    @PatchMapping("/password")
    public ResponseEntity<Response> updatePassword(@AuthenticationPrincipal Auth auth,
                                                   @RequestBody @Valid PasswordRequest passwordRequest) {
        myPageService.updatePassword(auth.getId(), passwordRequest.getPassword());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("비밀번호가 수정되었습니다.")
                        .build());
    }
}
