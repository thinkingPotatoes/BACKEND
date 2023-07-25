package com.talkingPotatoes.potatoesProject.movie.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieSearchDto {
    private String docId;

    private String title;

    private String poster;

    private String prodYear;
}
