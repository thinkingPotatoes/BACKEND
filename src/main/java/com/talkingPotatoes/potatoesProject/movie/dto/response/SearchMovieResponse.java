package com.talkingPotatoes.potatoesProject.movie.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchMovieResponse {
    private String docId;

    private String title;

    private String poster;

    private String prodYear;
}
