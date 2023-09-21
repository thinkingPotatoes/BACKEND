package com.talkingPotatoes.potatoesProject.blog.controller;

import com.talkingPotatoes.potatoesProject.blog.dto.CommentDto;
import com.talkingPotatoes.potatoesProject.blog.dto.request.CommentRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.response.GetCommentResponse;
import com.talkingPotatoes.potatoesProject.blog.mapper.CommentDtoMapper;
import com.talkingPotatoes.potatoesProject.blog.service.CommentService;
import com.talkingPotatoes.potatoesProject.common.dto.response.ListResponse;
import com.talkingPotatoes.potatoesProject.common.dto.response.Response;
import com.talkingPotatoes.potatoesProject.user.dto.Auth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentDtoMapper commentDtoMapper;

    private final CommentService commentService;

    /* 댓글 생성 */
    @PostMapping("/{articleId}")
    public ResponseEntity<Response> createComment(@AuthenticationPrincipal Auth auth,
                                                  @PathVariable UUID articleId,
                                                  @RequestBody @Valid CommentRequest createCommentRequest) {
        CommentDto commentDto = commentDtoMapper.fromCreateCommentRequest(auth.getId(), articleId, createCommentRequest);
        commentService.createComment(commentDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("댓글이 정상 등록되었습니다.")
                        .build());
    }

    /* 댓글 수정 */
    @PutMapping("/{articleId}/{id}")
    public  ResponseEntity<Response> updateComment(@AuthenticationPrincipal Auth auth,
                                                   @PathVariable("articleId") UUID articleId,
                                                   @PathVariable("id") UUID id,
                                                   @RequestBody @Valid CommentRequest commentRequest) {
        CommentDto commentDto = commentDtoMapper.fromUpdateCommentRequest(auth.getId(), id, articleId, commentRequest);

        commentService.updateComment(commentDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("댓글이 정상 등록되었습니다.")
                        .build());
    }

    /* 댓글 삭제 */
    @DeleteMapping("/{articleId}/{id}")
    public ResponseEntity<Response> deleteArticle(@AuthenticationPrincipal Auth auth,
                                                  @PathVariable("id") UUID id) {
        commentService.deleteComment(auth.getId(), id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("글이 정상 삭제되었습니다.")
                        .build());
    }

    /* 리뷰별 댓글 조회 */
    @GetMapping("/get/{articleId}")
    public ResponseEntity<Response> getCommentByArticleId(@PathVariable UUID articleId,
                                                          @PageableDefault(size = 10)
                                                          @SortDefault.SortDefaults({
                                                                  @SortDefault(sort = "updatedAt", direction = Sort.Direction.DESC)})
                                                          Pageable pageable) {
        Page<CommentDto> commentDtoList = commentService.getCommentByArticleId(articleId, pageable);

        List<GetCommentResponse> getCommentResponses = commentDtoMapper.toGetCommentResponse(commentDtoList.getContent());
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("블로그 글의 댓글이 정상 조회되었습니다.")
                        .data(ListResponse.builder()
                                .list(getCommentResponses)
                                .totalCnt(getCommentResponses.size())
                                .curPage(pageable.getPageNumber())
                                .build())
                        .build()
                );
    }

}
