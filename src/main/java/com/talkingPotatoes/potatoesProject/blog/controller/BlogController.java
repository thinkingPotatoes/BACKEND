package com.talkingPotatoes.potatoesProject.blog.controller;

import com.talkingPotatoes.potatoesProject.blog.dto.BlogDto;
import com.talkingPotatoes.potatoesProject.blog.mapper.BlogDtoMapper;
import com.talkingPotatoes.potatoesProject.blog.service.BlogService;
import com.talkingPotatoes.potatoesProject.common.dto.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
@Slf4j
public class BlogController {
    private final BlogService blogService;
    private final BlogDtoMapper blogDtoMapper;

    /* 블로그 조회 */
    @GetMapping
    public ResponseEntity<Response> getBlog(@RequestHeader(value = "userId") UUID loginId) {
        BlogDto blogDto = blogService.get(loginId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("Filog 정보 조회 완료되었습니다.")
                        .data(blogDto)
                        .build());
    }

    /* 블로그 제목 수정 */


    /* 블로그 관심사 수정 */
}
