package com.talkingPotatoes.potatoesProject.blog.controller;

import com.talkingPotatoes.potatoesProject.blog.dto.ArticleDto;
import com.talkingPotatoes.potatoesProject.blog.dto.CalendarDto;
import com.talkingPotatoes.potatoesProject.blog.dto.request.CreateArticleRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.request.SearchArticleRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.request.UpdateArticleRequest;
import com.talkingPotatoes.potatoesProject.blog.dto.response.GetArticleResponse;
import com.talkingPotatoes.potatoesProject.blog.dto.response.SearchArticleResponse;
import com.talkingPotatoes.potatoesProject.blog.mapper.ArticleDtoMapper;
import com.talkingPotatoes.potatoesProject.blog.service.ArticleService;
import com.talkingPotatoes.potatoesProject.common.dto.response.ListResponse;
import com.talkingPotatoes.potatoesProject.common.dto.response.Response;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.movie.service.MovieService;
import com.talkingPotatoes.potatoesProject.user.dto.Auth;
import com.talkingPotatoes.potatoesProject.user.dto.BlogUserDto;
import com.talkingPotatoes.potatoesProject.user.service.MyPageService;
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
@RequestMapping("/filog")
@RequiredArgsConstructor
@Slf4j
public class ArticleController {
    private final ArticleService articleService;
    private final MyPageService myPageService;
    private final MovieService movieService;

    private final ArticleDtoMapper articleDtoMapper;

    /* 블로그 글 등록 */
    @PostMapping("/article")
    public ResponseEntity<Response> createArticle(@AuthenticationPrincipal Auth auth,
                                                  @RequestBody @Valid CreateArticleRequest createArticleRequest) {
        ArticleDto articleDto = articleDtoMapper.fromCreateArticleRequest(auth.getId(), createArticleRequest);
        articleService.createArticle(articleDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("글이 정상 등록되었습니다.")
                        .build());
    }

    /* 블로그 글 수정 */
    @PutMapping("/article/{id}")
    public ResponseEntity<Response> updateArticle(@AuthenticationPrincipal Auth auth,
                                                  @RequestBody @Valid UpdateArticleRequest updateArticleRequest) {
        ArticleDto articleDto = articleDtoMapper.fromUpdateArticleRequest(auth.getId(), updateArticleRequest);

        articleService.updateArticle(articleDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("글이 정상 업데이트되었습니다.")
                        .build());
    }

    /* 블로그 글 삭제 */
    @DeleteMapping("/article/{id}")
    public ResponseEntity<Response> deleteArticle(@AuthenticationPrincipal Auth auth,
                                                  @PathVariable("id") UUID id) {
        articleService.deleteArticle(auth.getId(), id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("글이 정상 삭제되었습니다.")
                        .build());
    }

    /* 블로그 글 단일 조회 */
    @GetMapping("/search/{articleId}")
    public ResponseEntity<Response> getArticleById(@PathVariable UUID articleId) {
        ArticleDto articleDto = articleService.searchArticleById(articleId);
        BlogUserDto blogUserDto = myPageService.getBlogUser(articleDto.getUserId());
        MovieDto movieDto = movieService.selectMovie(articleDto.getMovieId());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("글이 정상 조회되었습니다.")
                        .data(articleDtoMapper.toGetArticleResponse(articleDto, blogUserDto, movieDto))
                        .build()
                );
    }

    /* 좋아요 수정 */
    @GetMapping("/{articleId}/like")
    public ResponseEntity<Response> updateLikes(@AuthenticationPrincipal Auth auth, @PathVariable("articleId") UUID articleId) {
        articleService.updateLikes(auth.getId(), articleId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("좋아요가 수정되었습니다.")
                        .build());
    }

    /* 영화 블로그 글 리스트 */
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<Response> getArticleByMovieId(@PathVariable String movieId,
                                                        @PageableDefault(size = 10)
                                                        @SortDefault.SortDefaults({
                                                                @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
                                                                @SortDefault(sort = "likeCnt", direction = Sort.Direction.DESC)
                                                        }) Pageable pageable) {
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
    public ResponseEntity<Response> getArticleByUserId(@AuthenticationPrincipal Auth auth,
                                                       @PageableDefault(size = 10)
                                                       @SortDefault.SortDefaults({
                                                               @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
                                                               @SortDefault(sort = "likeCnt", direction = Sort.Direction.DESC)
                                                       }) Pageable pageable) {
        Page<ArticleDto> articleDtoList = articleService.searchArticleByUserId(auth.getId(), pageable);

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
    public ResponseEntity<Response> getMyArticleByUserIdAndKeyword(@AuthenticationPrincipal Auth auth,
                                                                   @RequestBody @Valid SearchArticleRequest searchArticleRequest,
                                                                   @PageableDefault(size = 10)
                                                                   @SortDefault.SortDefaults({
                                                                           @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC),
                                                                           @SortDefault(sort = "likeCnt", direction = Sort.Direction.DESC)
                                                                   }) Pageable pageable) {
        Page<ArticleDto> articleDto = articleService.searchArticleByUserIdAndKeyword(auth.getId(), searchArticleRequest.getKeyword(), pageable);

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

    /* 월별 달력 */
    @GetMapping("/calendar/{year}/{month}")
    public ResponseEntity<Response> getCalendar(@AuthenticationPrincipal Auth auth,
                                                @PathVariable Integer year,
                                                @PathVariable Integer month) {
        List<CalendarDto> calendarDtoList = articleService.getCalendarFromMonth(auth.getId(), year, month);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message(month + "월의 데이터를 조회하였습니다")
                        .data(articleDtoMapper.toGetCalendarResponse(calendarDtoList))
                        .build()
                );
    }

    /* 날짜별 달력 */
    @GetMapping("/calendar/{year}/{month}/{day}")
    public ResponseEntity<Response> getCalendar(@AuthenticationPrincipal Auth auth,
                                                @PathVariable Integer year,
                                                @PathVariable Integer month,
                                                @PathVariable Integer day) {
        List<CalendarDto> calendarDtoList = articleService.getCalendarFromDay(auth.getId(), year, month, day);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message(day + "일자의 데이터를 조회하였습니다")
                        .data(articleDtoMapper.toGetCalendarDayResponse(calendarDtoList))
                        .build()
                );
    }
}
