package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.PosterDto;
import com.talkingPotatoes.potatoesProject.movie.dto.response.PosterUrlResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PosterDtoMapper {
    PosterUrlResponse toResponse(PosterDto dto);

    List<PosterUrlResponse> toResponse(List<PosterDto> dto);
}
