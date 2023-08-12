package com.talkingPotatoes.potatoesProject.movie.service;

import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface MovieService {

    Page<MovieDto> searchMovies(String keyword, Pageable pageable);

    MovieInfoDto selectMovie(String movieId);

}
