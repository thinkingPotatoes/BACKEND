package com.talkingPotatoes.potatoesProject.movie.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MovieInfoDto {
    MovieDto movieDto;

    List<StaffDto> staffDtoList;

    List<PosterDto> posterDtoList;
}
