package com.talkingPotatoes.potatoesProject.movie.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StarRatingDto {
    private Long id;

    private String movieId;

    private String userId;

    private Float star;
}
