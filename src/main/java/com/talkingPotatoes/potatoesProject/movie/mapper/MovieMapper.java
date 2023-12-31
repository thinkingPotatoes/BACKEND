package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    Movie toEntity(MovieDto movieDto);
    MovieDto toDto(Movie movie);
    List<Movie> toEntity(List<MovieDto> movieDtoList);
    List<MovieDto> toDto(List<Movie> movieList);
}
