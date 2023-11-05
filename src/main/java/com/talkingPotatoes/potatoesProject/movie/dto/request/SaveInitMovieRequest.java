package com.talkingPotatoes.potatoesProject.movie.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SaveInitMovieRequest {
    private String userId;

    private List<String> movieList;
}
