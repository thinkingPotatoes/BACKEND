package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.StarRatingDto;
import com.talkingPotatoes.potatoesProject.movie.entity.StarRating;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StarRatingMapper {
    StarRating toEntity(StarRating starRatingDto);

    StarRatingDto toDto(StarRating starRating);

    List<StarRating> toEntity(List<StarRating> starRatingDtoList);

    List<StarRatingDto> toDto(List<StarRating> starRatingList);
}
