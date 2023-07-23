package com.talkingPotatoes.potatoesProject.movie.controller;

import com.talkingPotatoes.potatoesProject.common.dto.response.Response;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieSearchDto;
import com.talkingPotatoes.potatoesProject.movie.dto.request.SearchRequest;
import com.talkingPotatoes.potatoesProject.movie.dto.response.MovieSearchResponse;
import com.talkingPotatoes.potatoesProject.movie.mapper.MovieDtoMapper;
import com.talkingPotatoes.potatoesProject.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private final MovieService movieService;
    private final MovieDtoMapper movieDtoMapper;

    // 영화검색
    @PostMapping("/search")
    public ResponseEntity<Response> search(@RequestBody SearchRequest searchRequest,
                                           @PageableDefault(size = 10, sort = "repRlsDate", direction = Sort.Direction.DESC) Pageable page) {
        Page<MovieSearchDto> movies = movieService.searchMovies(searchRequest.getKeyword(), page);

        MovieSearchResponse movieSearchResponse = MovieSearchResponse.builder()
                .movieSearchDtoList(movies.getContent())
                .totalCnt(movies.getTotalElements())
                .curPage(movies.getPageable().getPageNumber())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("영화 검색 완료하였습니다.")
                        .data(movieSearchResponse)
                        .build());
    }
}
