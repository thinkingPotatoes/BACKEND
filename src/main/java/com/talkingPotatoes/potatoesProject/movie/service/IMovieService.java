package com.talkingPotatoes.potatoesProject.movie.service;

import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;

import java.util.List;

public interface IMovieService {

    public void save(List<MovieDto> movieDtoList);

}
