package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.PosterDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Poster;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PosterMapper {
    Poster toEntity(PosterDto posterDto);

    PosterDto toDto(Poster poster);

    List<Poster> toEntity(List<PosterDto> posterDtoList);

    List<PosterDto> toDto(List<Poster> posterList);
}
