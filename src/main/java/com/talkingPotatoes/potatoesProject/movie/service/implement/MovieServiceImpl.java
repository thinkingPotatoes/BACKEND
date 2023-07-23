package com.talkingPotatoes.potatoesProject.movie.service.implement;

import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieSearchDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Movie;
import com.talkingPotatoes.potatoesProject.movie.mapper.MovieMapper;
import com.talkingPotatoes.potatoesProject.movie.repository.MovieQueryRepository;
import com.talkingPotatoes.potatoesProject.movie.repository.MovieRepository;
import com.talkingPotatoes.potatoesProject.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieQueryRepository movieQueryRepository;
    private final MovieMapper movieMapper;

    @Override
    public Page<MovieSearchDto> searchMovies(String keyword, Pageable pageable) {
        log.info("MovieServiceImpl::: searchMovies start");

        log.info("MovieServiceImpl::: searchMovies search " + keyword);
        Page<Movie> movies = movieQueryRepository.findByKeyword(keyword, pageable);

        log.info("MovieServiceImpl::: searchMovies finish");
        return new PageImpl<>(movieMapper.toSearchDto(movies.getContent()), movies.getPageable(), movies.getTotalElements());
    }
}
