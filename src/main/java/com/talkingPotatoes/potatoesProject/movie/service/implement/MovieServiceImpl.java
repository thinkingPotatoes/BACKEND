package com.talkingPotatoes.potatoesProject.movie.service.implement;

import com.talkingPotatoes.potatoesProject.common.exception.NotFoundException;
import com.talkingPotatoes.potatoesProject.movie.dto.BoxOfficeRateDto;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieInfoDto;
import com.talkingPotatoes.potatoesProject.movie.dto.StarRatingDto;
import com.talkingPotatoes.potatoesProject.movie.entity.*;
import com.talkingPotatoes.potatoesProject.movie.mapper.*;
import com.talkingPotatoes.potatoesProject.movie.repository.*;
import com.talkingPotatoes.potatoesProject.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final StaffRepository staffRepository;
    private final PosterRepository posterRepository;
    private final BoxOfficeRateRepository boxOfficeRateRepository;
    private final StarRatingRepository starRatingRepository;

    private final MovieQueryRepository movieQueryRepository;

    private final MovieMapper movieMapper;
    private final StaffMapper staffMapper;
    private final PosterMapper posterMapper;
    private final BoxOfficeRateMapper boxOfficeRateMapper;
    private final StarRatingMapper starRatingMapper;

    @Override
    public Page<MovieDto> searchMovies(String keyword, Pageable pageable) {
        Page<Movie> movies = movieQueryRepository.findByKeyword(keyword, pageable);

        return new PageImpl<>(movieMapper.toDto(movies.getContent()), movies.getPageable(), movies.getTotalElements());
    }

    @Override
    public MovieInfoDto selectMovieInfo(String movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("영화 정보가 없습니다."));
        List<Staff> staffList = staffRepository.findByDocId(movie.getDocId());
        List<Poster> posterList = posterRepository.findByDocId(movie.getDocId());

        return MovieInfoDto.builder()
                .movieDto(movieMapper.toDto(movie))
                .staffDtoList(staffMapper.toDto(staffList))
                .posterDtoList(posterMapper.toDto(posterList))
                .build();
    }

    @Override
    public MovieDto selectMovie(String movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("영화 정보가 없습니다."));

        return movieMapper.toDto(movie);
    }

    @Override
    public Page<BoxOfficeRateDto> getBoxOfficeRate(String curDt) {
        List<BoxOfficeRate> boxOfficeRates = boxOfficeRateRepository.findAllByTargetDt(curDt);

        return new PageImpl<>(boxOfficeRateMapper.toDto(boxOfficeRates));
    }

    @Override
    public List<StarRatingDto> selectStarRating(String userId) {
        List<StarRating> starRatingList = starRatingRepository.searchStarRatingByUserId(userId);
        return starRatingMapper.toDto(starRatingList);
    }
}
