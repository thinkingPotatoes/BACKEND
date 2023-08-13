package com.talkingPotatoes.potatoesProject.movie.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoxOfficeRateDto {
    private long id;

    private String movieId;

    private String movieNm;

    private Integer rate;

    private String posterUrl;

    private String audiAcc;
}
