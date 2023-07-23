package com.talkingPotatoes.potatoesProject.movie.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchRequest {
    private String keyword;
}
