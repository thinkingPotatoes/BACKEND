package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.StillDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Still;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StillMapper {
    Still toEntity(StillDto stillDto);

    StillDto toDto(Still still);

    List<Still> toEntity(List<StillDto> stillDtoList);

    List<StillDto> toDto(List<Still> stillList);
}
