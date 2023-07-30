package com.talkingPotatoes.potatoesProject.movie.dto.response;

import com.talkingPotatoes.potatoesProject.movie.dto.MovieSearchDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchMovieResponse {
    private List<MovieSearchDto> movieSearchDtoList;

    private long totalCnt;
    private int curPage;
}
