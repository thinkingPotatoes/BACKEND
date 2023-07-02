package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.MovieApiDto;
import com.talkingPotatoes.potatoesProject.movie.entity.MovieApi;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieApiMapper {
    MovieApi toEntity(MovieApiDto movieApiDto);
    MovieApiDto toDto(MovieApi movieApi);
    List<MovieApi> toEntity(List<MovieApiDto> movieApiDtoList);
    List<MovieApiDto> toDto(List<MovieApi> movieApiList);
}
