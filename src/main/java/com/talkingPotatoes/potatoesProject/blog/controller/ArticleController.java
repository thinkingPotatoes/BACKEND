package com.talkingPotatoes.potatoesProject.blog.controller;

import com.talkingPotatoes.potatoesProject.blog.dto.ArticleDto;
import com.talkingPotatoes.potatoesProject.blog.dto.ArticleSearchDto;
import com.talkingPotatoes.potatoesProject.blog.dto.request.CreateArticleRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.request.SearchArticleRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.request.UpdateArticleRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.response.GetArticleResponse;
import com.talkingPotatoes.potatoesProject.blog.dto.response.SearchArticleResponse;
import com.talkingPotatoes.potatoesProject.blog.mapper.ArticleDtoMapper;
import com.talkingPotatoes.potatoesProject.blog.service.ArticleService;
import com.talkingPotatoes.potatoesProject.common.dto.response.ListResponse;
import com.talkingPotatoes.potatoesProject.common.dto.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/blog")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleDtoMapper articleDtoMapper;

    /* 블로그 글 등록 */
    @PostMapping("/create")
    public ResponseEntity<Response> createArticle(@RequestHeader(value = "userId") UUID login_id,
                                                  @RequestBody @Valid CreateArticleRequest createArticleRequest) {
        ArticleDto articleDto = articleDtoMapper.fromCreateArticleRequest(login_id, createArticleRequest);
        articleService.createArticle(articleDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("글이 정상 등록되었습니다.")
                        .build());
    }

    /* 블로그 글 수정 */
    @PutMapping("/update")
    public ResponseEntity<Response> updateArticle(@RequestHeader(value = "userId") UUID loginId,
                                                  @RequestBody @Valid UpdateArticleRequest updateArticleRequest) {
        ArticleDto articleDto = articleDtoMapper.fromUpdateArticleRequest(loginId, updateArticleRequest);

        articleService.updateArticle(articleDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("글이 정상 업데이트되었습니다.")
                        .build());
    }

    /* 블로그 글 삭제 */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteArticle(@RequestHeader(value = "userId") UUID loginId,
                                                  @PathVariable("id") UUID id) {
        articleService.deleteArticle(loginId, id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("글이 정상 삭제되었습니다.")
                        .build());
    }

    /* 블로그 글 단일 조회 */
    @GetMapping("/search/{articleId}")
    public ResponseEntity<Response> getArticleById(@PathVariable UUID articleId) {
        ArticleDto articleDto = articleService.searchArticleById(articleId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("글이 정상 조회되었습니다.")
                        .data(articleDtoMapper.toGetArticleResponse(articleDto))
                        .build()
                );
    }

    /* 영화 블로그 글 리스트 */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<Response> getArticleByMovieId(@PathVariable String movieId,
                                                        @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ArticleDto> articleDtoList = articleService.searchArticleByMovieId(movieId, pageable);

        List<GetArticleResponse> getArticleResponseList = articleDtoMapper.toGetArticleResponse(articleDtoList.getContent());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("블로그 글 리스트가 정상 조회되었습니다.")
                        .data(ListResponse.builder()
                                .list(getArticleResponseList)
                                .totalCnt(articleDtoList.getTotalElements())
                                .curPage(pageable.getPageNumber())
                                .build())
                        .build()
                );
    }

    /* 유저 블로그 글 리스트 */
    @GetMapping("/user")
    public ResponseEntity<Response> getArticleByUserId(@RequestHeader(value = "userId") UUID loginId,
                                                       @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ArticleDto> articleDtoList = articleService.searchArticleByUserId(loginId, pageable);

        List<GetArticleResponse> getArticleResponseList = articleDtoMapper.toGetArticleResponse(articleDtoList.getContent());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("블로그 글 리스트가 정상 조회되었습니다.")
                        .data(ListResponse.builder()
                                .list(getArticleResponseList)
                                .totalCnt(articleDtoList.getTotalElements())
                                .curPage(pageable.getPageNumber())
                                .build())
                        .build()
                );
    }

    /* 내 블로그 검색 */
    @PostMapping("/search/my-article")
    public ResponseEntity<Response> getMyArticleByUserIdAndKeyword(@RequestHeader(value = "userId") UUID loginId,
                                                                   @RequestBody @Valid SearchArticleRequest searchArticleRequest,
                                                                   @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ArticleSearchDto> articleDto = articleService.searchArticleByUserIdAndKeyword(loginId, searchArticleRequest.getKeyword(), pageable);

        if (articleDto.getNumberOfElements() == 0) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Response.builder()
                            .message("일치하는 검색 결과가 없습니다.")
                            .build());
        }

        List<SearchArticleResponse> searchArticleResponseList = articleDtoMapper.toSearchMyArticleResponse(articleDto.getContent());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("내 블로그 검색 완료하였습니다.")
                        .data(ListResponse.builder()
                                .list(searchArticleResponseList)
                                .totalCnt(articleDto.getTotalElements())
                                .curPage(pageable.getPageNumber())
                                .build())
                        .build());
    }
}
