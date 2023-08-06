package com.talkingPotatoes.potatoesProject.movie.controller;

import com.talkingPotatoes.potatoesProject.common.dto.response.Response;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieInfoDto;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieSearchDto;
import com.talkingPotatoes.potatoesProject.movie.dto.request.SearchRequest;
import com.talkingPotatoes.potatoesProject.movie.dto.response.MovieSearchResponse;
import com.talkingPotatoes.potatoesProject.movie.dto.response.SelectMovieResponse;
import com.talkingPotatoes.potatoesProject.movie.mapper.MovieDtoMapper;
import com.talkingPotatoes.potatoesProject.movie.mapper.PosterDtoMapper;
import com.talkingPotatoes.potatoesProject.movie.mapper.StaffDtoMapper;
import com.talkingPotatoes.potatoesProject.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private final MovieService movieService;
    private final MovieDtoMapper movieDtoMapper;
    private final StaffDtoMapper staffDtoMapper;
    private final PosterDtoMapper posterDtoMapper;

    /* 영화검색 */
    @PostMapping("/search")
    public ResponseEntity<Response> search(@RequestBody SearchRequest searchRequest,
                                           @PageableDefault(size = 10, sort = "repRlsDate", direction = Sort.Direction.DESC) Pageable page) {
        Page<MovieDto> movies = movieService.searchMovies(searchRequest.getKeyword(), page);

        SearchMovieListResponse searchMovieListResponse = SearchMovieListResponse.builder()
                .searchMovieResponseList(movieDtoMapper.toSearchMovieResponse(movies.getContent()))
                .totalCnt(movies.getTotalElements())
                .curPage(movies.getPageable().getPageNumber())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("영화 검색 완료하였습니다.")
                        .data(searchMovieListResponse)
                        .build());
    }

    /* 영화조회 */
    @GetMapping("/{movie-id}")
    public ResponseEntity<Response> select(@PathVariable("movie-id") String movieId) {
        MovieInfoDto movieInfoDto = movieService.selectMovie(movieId);

        SelectMovieResponse response = movieDtoMapper.toSelectMovieResponse(movieInfoDto.getMovieDto());
        response.setStaffList(staffDtoMapper.toResponse(movieInfoDto.getStaffDtoList()));
        response.setPosterList(posterDtoMapper.toResponse(movieInfoDto.getPosterDtoList()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("영화 하나 조회 완료하였습니다.")
                        .data(response)
                        .build());
    }
}
