package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.PosterDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Poster;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PosterMapper {
    Poster toEntity(PosterDto staffDto);

    PosterDto toDto(Poster staff);

    List<Poster> toEntity(List<PosterDto> staffDtoList);

    List<PosterDto> toDto(List<Poster> staffList);
}
