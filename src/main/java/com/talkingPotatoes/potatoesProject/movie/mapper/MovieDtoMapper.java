package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.movie.dto.response.SearchMovieResponse;
import com.talkingPotatoes.potatoesProject.movie.dto.response.SelectMovieResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieDtoMapper {
    SearchMovieResponse toSearchMovieResponse(MovieDto movieSearchDto);

    List<SearchMovieResponse> toSearchMovieResponse(List<MovieDto> movieSearchDtoList);
  
    SelectMovieResponse toSelectMovieResponse(MovieDto movieDto);
}
