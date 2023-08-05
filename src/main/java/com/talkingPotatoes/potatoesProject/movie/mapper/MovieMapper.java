package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.movie.dto.MovieSearchDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Movie;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    Movie toEntity(MovieDto movieDto);
    MovieDto toDto(Movie movie);
    List<Movie> toEntity(List<MovieDto> movieDtoList);
    List<MovieDto> toDto(List<Movie> movieList);
    MovieSearchDto toSearchDto(Movie movie);
    List<MovieSearchDto> toSearchDto(List<Movie> content);
}
