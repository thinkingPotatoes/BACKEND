package com.talkingPotatoes.potatoesProject.movie.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PosterUrlResponse {
    private String posterUrl;
}
