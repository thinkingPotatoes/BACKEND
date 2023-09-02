package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.BoxOfficeRateDto;
import com.talkingPotatoes.potatoesProject.movie.entity.BoxOfficeRate;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoxOfficeRateMapper {
    BoxOfficeRateDto toDto(BoxOfficeRate boxOfficeRate);

    BoxOfficeRate toEntity(BoxOfficeRateDto boxOfficeRateDto);

    List<BoxOfficeRate> toEntity(List<BoxOfficeRateDto> boxOfficeRateDtoList);

    List<BoxOfficeRateDto> toDto(List<BoxOfficeRate> boxOfficeRateList);
}
