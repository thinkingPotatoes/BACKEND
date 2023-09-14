package com.talkingPotatoes.potatoesProject.movie.controller;

import com.talkingPotatoes.potatoesProject.common.dto.response.Response;
import com.talkingPotatoes.potatoesProject.movie.dto.BoxOfficeRateDto;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieInfoDto;
import com.talkingPotatoes.potatoesProject.movie.dto.StarRatingDto;
import com.talkingPotatoes.potatoesProject.movie.dto.request.SearchRequest;
import com.talkingPotatoes.potatoesProject.movie.dto.response.SearchMovieListResponse;
import com.talkingPotatoes.potatoesProject.movie.dto.response.SelectMovieResponse;
import com.talkingPotatoes.potatoesProject.movie.dto.response.StarRatingListResponse;
import com.talkingPotatoes.potatoesProject.movie.mapper.BoxOfficeRateMapper;
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

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private final MovieService movieService;
    private final MovieDtoMapper movieDtoMapper;
    private final StaffDtoMapper staffDtoMapper;
    private final PosterDtoMapper posterDtoMapper;
    private final BoxOfficeRateMapper boxOfficeRateMapper;

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
        MovieInfoDto movieInfoDto = movieService.selectMovieInfo(movieId);

        SelectMovieResponse response = movieDtoMapper.toSelectMovieResponse(movieInfoDto.getMovieDto());
        response.setStaffList(staffDtoMapper.toResponse(movieInfoDto.getStaffDtoList()));
        response.setPosterList(posterDtoMapper.toResponse(movieInfoDto.getPosterDtoList()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("영화 하나 조회 완료하였습니다.")
                        .data(response)
                        .build());
    }

    /* 박스오피스 조회 */
    @GetMapping("/boxOffice/{cur-date}")
    public ResponseEntity<Response> getBoxOffice(@PathVariable("cur-date") String curDt) {
        /* 박스오피스 전일자 조회 */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate prevDt = LocalDate.parse(curDt, formatter).minusDays(1);
        Page<BoxOfficeRateDto> boxOfficeRateDtoList = movieService.getBoxOfficeRate(prevDt.toString().replaceAll("-",""));

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("박스오피스 조회를 완료했습니다.")
                        .data(boxOfficeRateDtoList)
                        .build());
    }

    /* 영화평점 조회 */
    @GetMapping("/starRating/{user-id}")
    public ResponseEntity<Response> getStarRating(@PathVariable("user-id") String userId) {
        List<StarRatingDto> starRatingDtoList = movieService.selectStarRating(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Response.builder()
                        .message("userId에 따른 영화평점을 조회했습니다.")
                        .data(starRatingDtoList)
                        .build());
    }
}
