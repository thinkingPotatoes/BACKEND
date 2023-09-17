package com.talkingPotatoes.potatoesProject.movie.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchMovieListResponse {
    private List<SearchMovieResponse> searchMovieResponseList;

    private long totalCnt;
    private int curPage;
}
