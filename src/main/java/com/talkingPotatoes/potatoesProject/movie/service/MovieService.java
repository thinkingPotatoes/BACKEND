package com.talkingPotatoes.potatoesProject.movie.service;

import com.talkingPotatoes.potatoesProject.movie.dto.BoxOfficeRateDto;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieInfoDto;
import com.talkingPotatoes.potatoesProject.movie.dto.StarRatingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {

    Page<MovieDto> searchMovies(String keyword, Pageable pageable);

    MovieInfoDto selectMovieInfo(String movieId);

    MovieDto selectMovie(String movieId);

    Page<BoxOfficeRateDto> getBoxOfficeRate(String curDt);

    List<StarRatingDto> selectStarRating(String userId);
}
