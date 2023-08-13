package com.talkingPotatoes.potatoesProject.blog.controller;

import com.talkingPotatoes.potatoesProject.blog.dto.BlogDto;
import com.talkingPotatoes.potatoesProject.blog.dto.request.UpdateBlogRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.request.UpdateBlogTitleRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.request.UpdateUserGenreRequest;
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
@RequestMapping("/filog")
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
                        .data(blogDtoMapper.toResponse(blogDto))
                        .build());
    }

    /* 블로그 제목 수정 */
    @PatchMapping("/title")
    public ResponseEntity<Response> updateTitle(@RequestHeader(value = "userId") UUID loginId,
                                                @RequestBody UpdateBlogTitleRequest updateBlogTitleRequest) {
        blogService.updateTitle(loginId, updateBlogTitleRequest.getTitle());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("제목이 수정되었습니다.")
                        .build());
    }

    /* 블로그 관심사 수정 */
    @PatchMapping("/genre")
    public ResponseEntity<Response> updateGenre(@RequestHeader(value = "userId") UUID loginId,
                                                @RequestBody UpdateUserGenreRequest updateUserGenreRequest) {
        blogService.updateGenre(loginId, updateUserGenreRequest.getGenreList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("관심 장르가 수정되었습니다.")
                        .build());
    }


    /* 블로그 정보 수정 */
    @PatchMapping
    public ResponseEntity<Response> updateBlog(@RequestHeader(value = "userId") UUID loginId,
                                               @RequestBody UpdateBlogRequest updateBlogRequest) {
        blogService.update(blogDtoMapper.fromBlogRequest(loginId, updateBlogRequest));

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("Filog 정보가 수정되었습니다.")
                        .build());
    }
}
