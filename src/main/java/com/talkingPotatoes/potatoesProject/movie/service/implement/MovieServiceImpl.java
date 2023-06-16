package com.talkingPotatoes.potatoesProject.movie.service.implement;

import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Movie;
import com.talkingPotatoes.potatoesProject.movie.mapper.MovieMapper;
import com.talkingPotatoes.potatoesProject.movie.repository.MovieRepository;
import com.talkingPotatoes.potatoesProject.movie.service.IMovieService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements IMovieService {

    private final EntityManager em;
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    @Transactional
    public void save(List<MovieDto> movieDtoList) {
        movieRepository.saveAll(movieMapper.toEntity(movieDtoList));
    }



}
