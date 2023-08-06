package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.movie.dto.response.SelectMovieResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieDtoMapper {

    SelectMovieResponse toSelectMovieResponse(MovieDto movieDto);
}
