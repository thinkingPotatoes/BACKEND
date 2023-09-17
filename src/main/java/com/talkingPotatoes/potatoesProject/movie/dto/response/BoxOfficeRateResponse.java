package com.talkingPotatoes.potatoesProject.movie.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoxOfficeRateResponse {
    private String movieNm;

    private Integer rate;

    private String posterUrl;
}
